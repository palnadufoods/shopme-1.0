var dropdownCountries;
var dropdownStates;
var dropdownCities;

$(document).ready(function() {
	dropdownCountries = $("#country");
	dropdownStates = $("#state"); // listStates
	dropdownCities = $("#city");

	dropdownCountries.on("change", function() {
		console.log("Country Changed");
		loadStates4Country();
		$("#state").val("").focus();
	});
	
	dropdownStates.on("change",function(){
		console.log("State Changed");
		loadCities4State();
		$("#city").val("").focus();
	})
	
	//loadStates4Country();
});

function loadStates4Country() {
	selectedCountry = $("#country option:selected");
	countryId = selectedCountry.val();
	console.log("Country Id : "+ countryId);
	
	url = contextPath + "states/list_by_country/" + countryId;
	
	$.get(url, function(responseJson) {
		dropdownStates.empty();
		$("<option>").val(-1).text("--Select State--").appendTo(dropdownStates);	
		$.each(responseJson, function(index, state) {
			$("<option>").val(state.name).attr("state-id", state.id).text(state.name).appendTo(dropdownStates);
		});
	}).fail(function() {
		showErrorModal("Error loading states/provinces for the selected country.");
	})	
}

function loadCities4State() {
	/*selectedState = $("#state option:selected");
	stateName = selectedState.val();
	console.log("stateName: "+stateName);*/
	
	//selectedState = document.getElementById('state').options[document.getElementById('state').selectedIndex];
    //stateId = selectedState.getAttribute('state-id');
    
    stateId= $("#state option:selected").attr('state-id');
    console.log('Selected State ID:', stateId);
    console.log(' state val '+ $("#state option:selected").attr('state-id') );
	
	url = contextPath + "cities/list_by_state/" + stateId;
	
	$.get(url, function(responseJson) {
		dropdownCities.empty();
		
		console.log("Cities loaded : "+ responseJson);
		
		$.each(responseJson, function(index, city) {
			$("<option>").val(city.name).text(city.name).appendTo(dropdownCities);
		});
	}).fail(function() {
		showErrorModal("Error loading Cities/Districts for the selected State.");
	})	
		
}

	