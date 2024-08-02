package com.mkpits.bank.service.impl;

import com.mkpits.bank.dto.request.AccountRequest;
import com.mkpits.bank.dto.request.UserRequest;
import com.mkpits.bank.dto.response.*;
import com.mkpits.bank.model.*;
import com.mkpits.bank.repository.*;
import com.mkpits.bank.service.IUserService;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserCredentialRepository userCredentialRepository;
    @Autowired
    TransactionRepository transactionRepository;
@Autowired
TransferTransactionRepository transferTransactionRepository;
    @Autowired
    UserAddressRepo addressRepository;
    @Autowired
    UserStateRepo stateRepository;
    @Autowired
    UserCityRepo cityRepository;
    @Autowired
    UserDistrictRepo districtRepository;

    @Override
    public List<UserResponse> getAllUsers() {

        List<User> userList = (List<User>) userRepository.findAll();

        List<UserResponse> userRequestDtoList = new ArrayList<>();

        for (User user : userList) {
            UserResponse userGetResponseDto = convertUserModelToUserDtoGetResponse(user);
            userGetResponseDto.setId(user.getId());
            userRequestDtoList.add(userGetResponseDto);
        }
        return userRequestDtoList;
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        List<Account> accountList = (List<Account>) accountRepository.findAll();

        List<AccountResponse> accontRequestDtoList = new ArrayList<>();
        for (Account account : accountList) {
            AccountResponse accountResponse = convertAccountModelToAccountDtoGetResponse(account);
            accontRequestDtoList.add(accountResponse);
        }
        return accontRequestDtoList;
    }

    @Override
    public List<UserCredentialResponse> getAllUsersCredentials() {
        List<UserCredential> userCredentialList = (List<UserCredential>) userCredentialRepository.findAll();
        List<UserCredentialResponse> userCredentialRequestlist = new ArrayList<>();
        for (UserCredential userCredential : userCredentialList) {
            UserCredentialResponse userCredentialResponse = convertUserCredentialModelToUserCredentialDtoGetResponse(userCredential);
            userCredentialRequestlist.add(userCredentialResponse);
        }
        return userCredentialRequestlist;
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactionList = (List<Transaction>) transactionRepository.findAll();
        List<TransactionResponse> TransactionRequestlist = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            TransactionResponse transactionResponse = convertTransactionModelToTransactionDtoGetResponse(transaction);
            TransactionRequestlist.add(transactionResponse);
        }
        return TransactionRequestlist;

    }

    @Override
    public List<TransferResponse> getAllTransferTransactions() {
        List<TransferTransaction> transferTransactions = (List<TransferTransaction>) transferTransactionRepository.findAll();
        List<TransferResponse> TransferRequestlist = new ArrayList<>();
        for (TransferTransaction transferTransaction : transferTransactions) {
            TransferResponse transferTransactionResponse = convertTransactionModelToTransferTransactionDtoGetResponse(transferTransaction);
            TransferRequestlist.add(transferTransactionResponse);
        }
        return TransferRequestlist;
    }


    @Override
    public List<AccountResponse> getAccountByUserId(Integer userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream().map(this::convertAccountModelToAccountDtoGetResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserRequest registerUsers(UserRequest userRequest) {
        User user = userRequest.getUser();
        user.setCin(generateCIN());
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(1);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(1);
        userRepository.save(user);

        Account account = userRequest.getAccount();
        account.setUser(user);
        account.setUserId(user.getId());
        account.setAccountNumber(generateAccountNumber(userRequest));

        // Set the rate of interest based on the account type
        account.setRateOfInterest(calculateRateOfInterest(account.getAccountType()));
        account.setBranchId("EB124256");
        account.setOpeningDate(LocalDate.from(LocalDateTime.now()));
        // Set the closing date to 50 years from the opening date
        account.setClosingDate(LocalDate.from(LocalDateTime.now()).plusYears(50));
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy(1);
        account.setUpdatedAt(LocalDateTime.now());
        account.setUpdatedBy(1);
        accountRepository.save(account);

        UserCredential userCredential = userRequest.getUserCredential();
        // Set user in userCredential and save userCredential
        String uuid = UUID.randomUUID().toString();
        System.out.println("UUID: " + uuid);
//        final String computedPassword = Hashing.sha256()
//                .hashString(userCredential.getPassword(), StandardCharsets.UTF_8).toString() + uuid;
        userCredential.setUserId(user.getId());
        userCredential.setUser(user);
//        userCredential.setPassword(computedPassword);
        userCredential.setPasswordSalt(uuid);
        userCredential.setLoginDateTime(LocalDateTime.now());
        userCredential.setCreatedBy(1);
        userCredential.setCreatedAt(LocalDateTime.now());
        userCredential.setUpdatedBy(1);
        userCredential.setUpdatedAt(LocalDateTime.now());
        userCredentialRepository.save(userCredential);


// Set user in address and save address
        UserState userState = userRequest.getState();
        userState.setCode(Integer.parseInt(convertStateNameToCode(userState.getName())));
        userState.setCreatedBy(1);
        userState.setCreatedAt(LocalDateTime.now());
        userState.setUpdatedBy(1);
        userState.setUpdatedAt(LocalDateTime.now());
        stateRepository.save(userState);

        UserDistrict userDistrict = userRequest.getDistrict();
        userDistrict.setCode(Integer.parseInt(convertDistrictNameToCode(userDistrict.getName(), userState.getName(), userRequest.getCity().getName())));

        userDistrict.setStateId(userState.getId());
        userDistrict.setCreatedBy(1);
        userDistrict.setCreatedAt(LocalDateTime.now());
        userDistrict.setUpdatedBy(1);
        userDistrict.setUpdatedAt(LocalDateTime.now());
        districtRepository.save(userDistrict);

        UserCity userCity = userRequest.getCity();
        userCity.setCode(Integer.parseInt(convertCityNameToCode(userCity.getName(), userState.getName())));
        userCity.setDistrictId(userDistrict.getId());
        userCity.setCreatedBy(1);
        userCity.setCreatedAt(LocalDateTime.now());
        userCity.setUpdatedBy(1);
        userCity.setUpdatedAt(LocalDateTime.now());
        cityRepository.save(userCity);

        UserAddress userAddress = userRequest.getAddress();
        userAddress.setUserId(user.getId());  // Corrected to set user ID
        userAddress.setUser(user);
        userAddress.setCityId(userCity.getId());  // Corrected to set city ID
        userAddress.setDistrictId(userDistrict.getId());  // Corrected to set district ID
        userAddress.setStateId(userState.getId());
        userAddress.setCreatedBy(1);
        userAddress.setCreatedAt(LocalDateTime.now());
        userAddress.setUpdatedBy(1);
        userAddress.setUpdatedAt(LocalDateTime.now());
        addressRepository.save(userAddress);
        return userRequest;
    }

//    add account
public AccountRequest addAccount(AccountRequest accountRequest, Integer userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    Account account = new Account();
    account.setUserId(user.getId());
    account.setAccountNumber(newAccountNumber(userId)); // Implement this method to generate a unique account number
    account.setAccountType(accountRequest.getAccountType());
    account.setBalance(accountRequest.getBalance());

    // Set the rate of interest based on the account type
    account.setRateOfInterest(calculateRateOfInterest(account.getAccountType()));
    account.setBranchId("EB124256");
    account.setOpeningDate(LocalDate.now());
    account.setClosingDate(LocalDate.now().plusYears(50));
    account.setCreatedAt(LocalDateTime.now());
    account.setCreatedBy(user.getId()); // Replace with actual user ID
    account.setUpdatedAt(LocalDateTime.now());
    account.setUpdatedBy(user.getId()); // Replace with actual user ID

    accountRepository.save(account);

    return accountRequest;
}
    private String newAccountNumber(Integer userId) {
        Optional<Account> optionalAccount = accountRepository.findTopByUserIdOrderByAccountNumberDesc(userId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            String oldAccountNumber = account.getAccountNumber();
            try {
                long numericPart = Long.parseLong(oldAccountNumber); // Use Long for larger numbers
                long newAccountNumberLong = numericPart + 1; // Increment the numeric part
                String newAccountNumber = String.format("%012d", newAccountNumberLong); // Format back to a 12-digit string
                System.out.println("New Account Number: " + newAccountNumber); // Log the account number
                return newAccountNumber;
            } catch (NumberFormatException e) {
                throw new RuntimeException("Error parsing account number", e);
            }
        } else {
            String defaultAccountNumber = "000000000001"; // Default account number for new users
            System.out.println("Default Account Number: " + defaultAccountNumber); // Log the account number
            return defaultAccountNumber;
        }
    }



    @Override
    public void updateUserData(Long userId, UserRequest userRequest) {

        Optional<User> userOptional = userRepository.findById(Math.toIntExact(userId));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userRequest.getUser().getFirstName());
            user.setMiddleName(userRequest.getUser().getMiddleName());
            user.setLastName(userRequest.getUser().getLastName());
            user.setMobileNumber(userRequest.getUser().getMobileNumber());
            user.setEmail(userRequest.getUser().getEmail());
            user.setGender(userRequest.getUser().getGender());
            user.setDateOfBirth(userRequest.getUser().getDateOfBirth());
            user.setAdhaarCard(userRequest.getUser().getAdhaarCard());
            user.setUpdatedAt(LocalDateTime.now());
            user.setUpdatedBy(1);
            userRepository.save(user);
        }

        Optional<UserCredential> userCredentialOptional = userCredentialRepository.findByUserId(Math.toIntExact(userId));
        if (userCredentialOptional.isPresent()) {
            UserCredential userCredential = userCredentialOptional.get();
            userCredential.setUserName(userRequest.getUserCredential().getUserName());
            userCredential.setPassword(userRequest.getUserCredential().getPassword());
            userCredential.setUpdatedAt(LocalDateTime.now());
            userCredential.setUpdatedBy(1);
            userCredentialRepository.save(userCredential);
        }

//        Optional<Account> accountOptional = accountRepository.findByUserId(Math.toIntExact(userId));
//        if (accountOptional.isPresent()) {
//            Account account = accountOptional.get();
//            account.setAccountType(userRequest.getAccount().getAccountType());
//            account.setBalance(userRequest.getAccount().getBalance());
//            account.setUpdatedAt(LocalDateTime.now());
//            account.setUpdatedBy(1);
//            accountRepository.save(account);
//        }

        // Update Address
        Optional<UserAddress> userAddressOptional = addressRepository.findByUserId(Math.toIntExact(userId));
        if (userAddressOptional.isPresent()) {
            UserAddress userAddress = userAddressOptional.get();
            userAddress.setAddress(userRequest.getAddress().getAddress());
            userAddress.setPincode(userRequest.getAddress().getPincode());
            userAddress.setUpdatedAt(LocalDateTime.now());
            userAddress.setUpdatedBy(1);

            // Update State
            if (userRequest.getAddress().getState() != null) {
                if (userRequest.getAddress().getState().getId() != null) {
                    Optional<UserState> userStateOptional = stateRepository.findById(userRequest.getAddress().getState().getId());
                    userStateOptional.ifPresent(userState -> {
                        userState.setName(userRequest.getAddress().getState().getName());
                        userState.setUpdatedAt(LocalDateTime.now());
                        userState.setUpdatedBy(1);
                        stateRepository.save(userState);
                    });
                }
            }

            // Update City
            if (userRequest.getAddress().getCity() != null) {
                if (userRequest.getAddress().getCity().getId() != null) {
                    Optional<UserCity> userCityOptional = cityRepository.findById(userRequest.getAddress().getCity().getId());
                    userCityOptional.ifPresent(userCity -> {
                        userCity.setName(userRequest.getAddress().getCity().getName());
                        userCity.setUpdatedAt(LocalDateTime.now());
                        userCity.setUpdatedBy(1);
                        cityRepository.save(userCity);
                    });
                }
            }

            // Update District
            if (userRequest.getAddress().getDistrict() != null) {
                if (userRequest.getAddress().getDistrict().getId() != null) {
                    Optional<UserDistrict> userDistrictOptional = districtRepository.findById(userRequest.getAddress().getDistrict().getId());
                    userDistrictOptional.ifPresent(userDistrict -> {
                        userDistrict.setName(userRequest.getAddress().getDistrict().getName());
                        userDistrict.setUpdatedAt(LocalDateTime.now());
                        userDistrict.setUpdatedBy(1);
                        districtRepository.save(userDistrict);
                    });
                }
            }

            addressRepository.save(userAddress);
        }
    }

    public List<AccountResponse> getAccountDetailsByUserId(Integer userId) {
        List<Account> accounts = accountRepository.findAllByUserId(userId);
        List<AccountResponse> accountResponses = new ArrayList<>();

        for (Account account : accounts) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setAccountType(account.getAccountType());
            accountResponse.setAccountNumber(account.getAccountNumber());
            accountResponse.setBalance(account.getBalance());
            accountResponse.setRateOfInterest(account.getRateOfInterest());
            accountResponses.add(accountResponse);
        }

        return accountResponses;
    }



    public String generateAccountNumber(UserRequest userRequest) {

        UserState userState = userRequest.getState();
        UserCity userCity = userRequest.getCity();
        UserDistrict userDistrict = userRequest.getDistrict();
        String stateName = userState.getName();
        String cityName = userCity.getName();
        String districtName = userDistrict.getName();

        // Convert names to codes
        String stateCode = convertStateNameToCode(stateName);
        String cityCode = convertCityNameToCode(cityName, stateName);
        String districtCode = convertDistrictNameToCode(districtName, stateName, cityName);
        // Get the highest existing number for this combination
        String prefix = String.format("%s%s%s", stateCode, cityCode, districtCode);
        String lastAccountNumber = accountRepository.findMaxAccountNumberByPrefix(prefix);

        // Increment the last part
        int newNumber = 1; // Default start number
        if (lastAccountNumber != null && lastAccountNumber.startsWith(prefix)) {
            String lastPart = lastAccountNumber.substring(prefix.length());
            newNumber = Integer.parseInt(lastPart) + 1;
        }

        // Format codes into account number
        String accountNumber = String.format("%s%04d", prefix, newNumber);

        return accountNumber;


    }

    private String convertDistrictNameToCode(String districtName, String stateName, String cityName) {

// Example logic to convert district name to code based on state and city
        return switch (stateName.toLowerCase()) {
            case "maharashtra" -> switch (cityName.toLowerCase()) {
                case "mumbai" -> switch (districtName.toLowerCase()) {
                    case "mumbai city" -> "1111";
                    case "mumbai suburban" -> "1112";
                    default -> "0000"; // Default or error handling
                };
                case "pune" -> switch (districtName.toLowerCase()) {
                    case "pune city" -> "1113";
                    case "pimpri-chinchwad" -> "1114";
                    default -> "0000"; // Default or error handling
                };
                case "nagpur" -> switch (districtName.toLowerCase()) {
                    case "nagpur city" -> "1115";
                    case "nagpur rural" -> "1116";
                    case "katol" -> "1117";
                    case "hingna" -> "1118";
                    case "kalmeshwar" -> "1119";
                    case "kamptee" -> "1120";
                    case "parseoni" -> "1121";
                    case "savner" -> "1122";
                    case "umred" -> "1123";
                    case "ramtek" -> "1124";
                    case "mouda" -> "1125";
                    case "bhiwapur" -> "1126";
                    case "narkhed" -> "1127";
                    default -> "0000"; // Default or error handling
                };
                default -> "0000"; // Default or error handling
            };
            case "gujarat" -> switch (cityName.toLowerCase()) {
                case "ahmedabad" -> switch (districtName.toLowerCase()) {
                    case "ahmedabad city" -> "2221";
                    case "ahmedabad rural" -> "2222";
                    default -> "0000"; // Default or error handling
                };
                case "surat" -> switch (districtName.toLowerCase()) {
                    case "surat city" -> "2223";
                    case "surat rural" -> "2224";
                    default -> "0000"; // Default or error handling
                };
                case "vadodara" -> switch (districtName.toLowerCase()) {
                    case "vadodara city" -> "2225";
                    case "vadodara rural" -> "2226";
                    default -> "0000"; // Default or error handling
                };
                default -> "0000"; // Default or error handling
            };
            case "karnataka" -> switch (cityName.toLowerCase()) {
                case "bengaluru" -> switch (districtName.toLowerCase()) {
                    case "bengaluru urban" -> "3331";
                    case "bengaluru rural" -> "3332";
                    default -> "0000"; // Default or error handling
                };
                case "mysuru" -> switch (districtName.toLowerCase()) {
                    case "mysuru city" -> "3333";
                    case "mysuru rural" -> "3334";
                    default -> "0000"; // Default or error handling
                };
                case "mangaluru" -> switch (districtName.toLowerCase()) {
                    case "mangaluru city" -> "3335";
                    case "mangaluru rural" -> "3336";
                    default -> "0000"; // Default or error handling
                };
                default -> "0000"; // Default or error handling
            };
            case "punjab" -> switch (cityName.toLowerCase()) {
                case "amritsar" -> switch (districtName.toLowerCase()) {
                    case "amritsar city" -> "4441";
                    case "amritsar rural" -> "4442";
                    default -> "0000"; // Default or error handling
                };
                case "ludhiana" -> switch (districtName.toLowerCase()) {
                    case "ludhiana city" -> "4443";
                    case "ludhiana rural" -> "4444";
                    default -> "0000"; // Default or error handling
                };
                case "chandigarh" -> switch (districtName.toLowerCase()) {
                    case "chandigarh city" -> "4445";
                    case "chandigarh suburban" -> "4446";
                    default -> "0000"; // Default or error handling
                };
                default -> "0000"; // Default or error handling
            };
            case "uttarpradesh" -> switch (cityName.toLowerCase()) {
                case "lucknow" -> switch (districtName.toLowerCase()) {
                    case "lucknow city" -> "5551";
                    case "lucknow rural" -> "5552";
                    default -> "0000"; // Default or error handling
                };
                case "kanpur" -> switch (districtName.toLowerCase()) {
                    case "kanpur city" -> "5553";
                    case "kanpur rural" -> "5554";
                    default -> "0000"; // Default or error handling
                };
                case "varanasi" -> switch (districtName.toLowerCase()) {
                    case "varanasi city" -> "5555";
                    case "varanasi rural" -> "5556";
                    default -> "0000"; // Default or error handling
                };
                default -> "0000"; // Default or error handling
            };
            case "madhyapradesh" -> switch (cityName.toLowerCase()) {
                case "bhopal" -> switch (districtName.toLowerCase()) {
                    case "bhopal city" -> "6661";
                    case "bhopal rural" -> "6662";
                    default -> "0000"; // Default or error handling
                };
                case "indore" -> switch (districtName.toLowerCase()) {
                    case "indore city" -> "6663";
                    case "indore rural" -> "6664";
                    default -> "0000"; // Default or error handling
                };
                case "gwalior" -> switch (districtName.toLowerCase()) {
                    case "gwalior city" -> "6665";
                    case "gwalior rural" -> "6666";
                    default -> "0000"; // Default or error handling
                };
                default -> "0000"; // Default or error handling
            };
            default -> "0000"; // Default or error handling
        };
    }

    private String convertCityNameToCode(String cityName, String stateName) {
        // Example logic to convert city name to code based on state
        return switch (stateName.toLowerCase()) {
            case "maharashtra" -> switch (cityName.toLowerCase()) {
                case "mumbai" -> "12";
                case "pune" -> "13";
                case "nagpur" -> "14";
                default -> "00"; // Default or error handling
            };
            case "gujarat" -> switch (cityName.toLowerCase()) {
                case "ahmedabad" -> "15";
                case "surat" -> "16";
                case "vadodara" -> "17";
                default -> "00"; // Default or error handling
            };
            case "karnataka" -> switch (cityName.toLowerCase()) {
                case "bengaluru" -> "18";
                case "mysuru" -> "19";
                case "mangaluru" -> "20";
                default -> "00"; // Default or error handling
            };
            case "punjab" -> switch (cityName.toLowerCase()) {
                case "amritsar" -> "21";
                case "ludhiana" -> "22";
                case "chandigarh" -> "23";
                default -> "00"; // Default or error handling
            };
            case "uttarpradesh" -> switch (cityName.toLowerCase()) {
                case "lucknow" -> "24";
                case "kanpur" -> "25";
                case "varanasi" -> "26";
                default -> "00"; // Default or error handling
            };
            case "madhyapradesh" -> switch (cityName.toLowerCase()) {
                case "bhopal" -> "27";
                case "indore" -> "28";
                case "gwalior" -> "29";
                default -> "00"; // Default or error handling
            };
            // Add more cases for other states as needed
            default -> "00"; // Default or error handling
        };

    }

    private String convertStateNameToCode(String stateName) {
        // Example logic to convert state name to code
        return switch (stateName.toLowerCase()) {
            case "maharashtra" -> "11";
            case "gujarat" -> "13";
            case "karnataka" -> "14";
            case "punjab" -> "15";
            case "uttar pradesh" -> "16";
            case "madhya pradesh" -> "17";
            // Add more states as needed
            default -> "00"; // Default or error handling
        };
    }


    //    get total users
    @Override
    public long getTotalUsers() {
        return userRepository.count();
    }

    //    get total transactions
    @Override
    public long getTotalTransactions() {
        return transactionRepository.count();
    }

    //    get total accounts
    @Override
    public long getTotalAccounts() {
        return accountRepository.count();
    }

    //  get accounts created today
    @Override
    public long getTotalAccountsCreatedToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        return accountRepository.countByCreatedAtBetween(startOfDay, endOfDay);
    }


    // Method to get total accounts by user ID
    public long getTotalAccountsByUserId(Integer userId) {
        return accountRepository.countByUserId(userId);
    }

    // Method to get total transactions by user ID
    public long getTotalTransactionsByUserId(Integer userId) {
        return transactionRepository.countByUserId(userId);
    }
//get total balance of all accounts
    public BigDecimal getTotalBalanceByUserId(Integer userId) {
        List<Account> accounts = accountRepository.findAccountBalanceByUserId(userId);
        return accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    // Get all accounts by user ID
    public Optional<Account> getAccountsByUserId(Integer userId) {
        return accountRepository.findAccountsByUserId(userId);
    }


    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }



    //    get daily user in chart graph
    @Override
    public List<Map<String, Object>> getDailyUserData() {
        List<Map<String, Object>> dailyUserData = new ArrayList<>();

        List<Object[]> results = userRepository.findDailyUserData();
        for (Object[] result : results) {
            Map<String, Object> data = new HashMap<>();
            data.put("date", result[0]);
            data.put("userCount", result[1]);
            dailyUserData.add(data);
        }

        return dailyUserData;
    }

    //get total balance of accounts
    @Override
    public BigDecimal getTotalBalance() {
        return accountRepository.findTotalBalance();
    }

    //last 5 users
    @Override
    public List<User> getLast5Users() {
        return userRepository.findTop5ByOrderByUsersDateTimeDesc();
    }

    //delete user by id
    @Override
    public void deleteUserById(Long id) {

        userRepository.deleteById(Math.toIntExact(id));

    }

    //get user by id
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(Math.toIntExact(id));
    }


    //    automatically set rate of interest using accountType
    private BigDecimal calculateRateOfInterest(String accountType) {
        return switch (accountType) {
            case "Saving" -> new BigDecimal("3.4");
            case "Current" -> new BigDecimal("5.2");
            case "FD" -> new BigDecimal("7.1");
            case "RD" -> new BigDecimal("6.5");
            default -> throw new IllegalArgumentException("Invalid account type: " + accountType);
        };
    }


    //cin based on current datetime
    private String generateCIN() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(formatter);
    }


    private TransactionResponse convertTransactionModelToTransactionDtoGetResponse(Transaction transaction) {
        User user = transaction.getUser();

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse = TransactionResponse.builder().id(transaction.getId()).fullName(user.getFirstName() + ' ' + user.getLastName()).transactionType(String.valueOf(TransactionType.valueOf(transaction.getTransactionType().name()))).accountNumber(transaction.getAccountNumber()).amount(transaction.getAmount())
                .transactionDateTime(transaction.getTransactionDateTime()).transactionStatus(transaction.getTransactionStatus()).build();
        return transactionResponse;
    }

    private TransferResponse convertTransactionModelToTransferTransactionDtoGetResponse(TransferTransaction transferTransaction) {
User user=transferTransaction.getUser();
        TransferResponse transferResponse=new TransferResponse();
        transferResponse=TransferResponse.builder().id(transferTransaction.getId()).fullName(user.getFirstName() + ' ' + user.getLastName()).senderId(transferTransaction.getSenderId())
                .senderAccountNumber(transferTransaction.getSenderAccountNumber())
                .receiverId(transferTransaction.getReceiverId())
                .receiverAccountNumber(transferTransaction.getReceiverAccountNumber())
                .transferredAmount(transferTransaction.getTransferAmount())
                .senderBalance(transferTransaction.getSenderBalance())
                .receiverBalance(transferTransaction.getReceiverBalance()) .build();
        return transferResponse;
    }

    private UserCredentialResponse convertUserCredentialModelToUserCredentialDtoGetResponse(UserCredential userCredential) {


        User user = userCredential.getUser(); // Assuming UserCredential has a reference to User
        String fullName = user.getFirstName() + " " + (user.getMiddleName() != null ? user.getMiddleName() + " " : "") + user.getLastName();

        UserCredentialResponse userCredentialResponse = new UserCredentialResponse();
        userCredentialResponse = UserCredentialResponse.builder().id(userCredential.getId())
                .userName(userCredential.getUserName()).password(userCredential.getPassword())
                .loginDateTime(userCredential.getLoginDateTime()).createdAt(userCredential.getCreatedAt()).updatedAt(userCredential.getUpdatedAt()).fullName(fullName).build();
        return userCredentialResponse;
    }

    private AccountResponse convertAccountModelToAccountDtoGetResponse(Account account) {
        AccountResponse accountResponse = new AccountResponse();
        User user = account.getUser();
        accountResponse = AccountResponse.builder().id(account.getId()).userId(account.getUserId()).name(user.getFirstName() + ' ' + user.getLastName())
                .accountType(account.getAccountType()).balance(account.getBalance()).accountNumber(account.getAccountNumber())
                .rateOfInterest(account.getRateOfInterest()).branchId(account.getBranchId()).openingDate(account.getOpeningDate()).closingDate(account.getClosingDate()).build();
        return accountResponse;

    }


    private UserResponse convertUserModelToUserDtoGetResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse = UserResponse.builder()
                .firstName(user.getFirstName()).middleName(user.getMiddleName()).lastName(user.getLastName())
                .mobileNumber(user.getMobileNumber()).email(user.getEmail()).gender(user.getGender()).dateOfBirth(user.getDateOfBirth()
                ).cin(user.getCin()).aadhaarCard(user.getAdhaarCard()).createdAt(user.getCreatedAt()).updatedAt(user.getUpdatedAt()).build();
        return userResponse;

    }
}
