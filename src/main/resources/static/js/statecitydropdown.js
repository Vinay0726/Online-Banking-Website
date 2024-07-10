 const stateCityMap = {
   "Maharashtra": ["Mumbai", "Pune", "Nagpur"],
   "Gujarat": ["Ahmedabad", "Surat", "Vadodara"],
   "Karnataka": ["Bengaluru", "Mysuru", "Mangaluru"],
   "Punjab": ["Amritsar", "Ludhiana", "Chandigarh"],
   "Uttarpradesh": ["Lucknow", "Kanpur", "Varanasi"],
   "Madhyapradesh": ["Bhopal", "Indore", "Gwalior"],
 };

 const cityDistrictMap = {
   "Mumbai": ["Mumbai City", "Mumbai Suburban"],
   "Pune": ["Pune City", "Pimpri-Chinchwad"],
   "Nagpur": ["Nagpur City", "Nagpur Rural", "Katol", "Hingna", "Kalmeshwar","Kamptee", "Parseoni","Savner","Umred","Ramtek","Mouda","Bhiwapur", "Narkhed"],
   "Ahmedabad": ["Ahmedabad City", "Ahmedabad Rural"],
   "Surat": ["Surat City", "Surat Rural"],
   "Vadodara": ["Vadodara City", "Vadodara Rural"],
   "Bengaluru": ["Bengaluru Urban", "Bengaluru Rural"],
   "Mysuru": ["Mysuru City", "Mysuru Rural"],
   "Mangaluru": ["Mangaluru City", "Mangaluru Rural"],
   "Amritsar": ["Amritsar City", "Amritsar Rural"],
   "Ludhiana": ["Ludhiana City", "Ludhiana Rural"],
   "Chandigarh": ["Chandigarh City", "Chandigarh Suburban"],
   "Lucknow": ["Lucknow City", "Lucknow Rural"],
   "Kanpur": ["Kanpur City", "Kanpur Rural"],
   "Varanasi": ["Varanasi City", "Varanasi Rural"],
   "Bhopal": ["Bhopal City", "Bhopal Rural"],
   "Indore": ["Indore City", "Indore Rural"],
   "Gwalior": ["Gwalior City", "Gwalior Rural"],
 };

 function populateCities() {
   const stateSelect = document.getElementById("state");
   const citySelect = document.getElementById("city");
   const selectedState = stateSelect.value;

   // Clear previous city options
   citySelect.innerHTML = '<option value="" disabled selected>Select City</option>';

   // Populate cities based on selected state
   if (selectedState && stateCityMap[selectedState]) {
     stateCityMap[selectedState].forEach(city => {
       const option = document.createElement("option");
       option.text = city;
       citySelect.add(option);
     });
   }
   // Clear district options
   document.getElementById("district").innerHTML = '<option value="" disabled selected>Select District</option>';
 }

 function populateDistricts() {
   const citySelect = document.getElementById("city");
   const districtSelect = document.getElementById("district");
   const selectedCity = citySelect.value;

   // Clear previous district options
   districtSelect.innerHTML = '<option value="" disabled selected>Select District</option>';

   // Populate districts based on selected city
   if (selectedCity && cityDistrictMap[selectedCity]) {
     cityDistrictMap[selectedCity].forEach(district => {
       const option = document.createElement("option");
       option.value = district.toLowerCase();
       option.text = district;
       districtSelect.add(option);
     });
   }
 }

 document.addEventListener('DOMContentLoaded', () => {
   document.getElementById('state').addEventListener('change', populateCities);
   document.getElementById('city').addEventListener('change', populateDistricts);
 });
