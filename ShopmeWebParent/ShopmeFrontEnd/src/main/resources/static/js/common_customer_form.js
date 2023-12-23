var dropDownCountry;
var dataListState;
var fieldState;
var dropdownCity;
var dropdownState;

$(document).ready(function() {
	dropDownCountry = $("#country");
	dataListState = $("#listStates");
	fieldState = $("#state");
	dropdownState = $("#state");
	dropdownCity=$("#city");
	
	dropDownCountry.on("change", function() {
		loadStatesForCountry();
		fieldState.val("").focus();
	});
	
	dataListState.on("change",function(){
		console.log("Changeed State");
		loadCitiesForState();
		dropdownCity.val("").focus();
	});
	
	/*$("#listCountries-register-from").on("change", function() {
		loadStatesForCountry();
		fieldState.val("").focus();
	});*/
	
	//loadStates($('#country').val());
	//loadStates($('#listCountries-register-from').val());
	
});

function loadStatesForCountry() {
	selectedCountry = $("#country option:selected");
	countryId = selectedCountry.val();
	console.log('Country Id in loadStatesForCountry(): '+countryId);
	loadStates(countryId);
	/*url = contextPath + "settings/list_states_by_country/" + countryId;
	
	$.get(url, function(responseJSON) {
		dataListState.empty();
		
		$.each(responseJSON, function(index, state) {
			$("<option>").val(state.name).text(state.name).appendTo(dataListState);
		});
		
	}).fail(function() {
		alert('failed to connect to the server!');
	});*/
}

function loadStates(countryId){
	console.log('Country Id: '+countryId);
	
	url = contextPath + "settings/list_states_by_country/" + countryId;
	
	$.get(url, function(responseJSON) {
		dataListState.empty();
		
		$("<option>").val("").text("--Select State--").prop("disabled", true).prop("selected", true).appendTo(dataListState);
		
		$.each(responseJSON, function(index, state) {
			$("<option>").val(state.name).attr("state-id",state.id).text(state.name).appendTo(dataListState);
		});
		
	}).fail(function() {
		alert('failed to connect to the server!');
	});
}

function loadCitiesForState(){
	
	/*stateId= $("#state option:selected").attr('state-id');
	console.log("State Id 0: "+ stateId);*/
	stateId = $("#listStates option:selected").attr('state-id');
	console.log("State Id 1: "+ stateId);

	url = contextPath + "settings/list_cities_by_state/" + stateId;
	
	$.get(url, function(responseJSON) {
		dropdownCity.empty();
		/*$("<option>").val(-1).text("--Select District--").appendTo(dropdownCity);*/
		$("<option>").val("").text("--Select City--").prop("disabled", true).prop("selected", true).appendTo(dropdownCity);
		
		$.each(responseJSON, function(index, city) {
			$("<option>").val(city.name).text(city.name).appendTo(dropdownCity);
		});
		
	}).fail(function() {
		alert('failed to connect to the server!');
	});
}

function checkPasswordMatch(confirmPassword) {
	if (confirmPassword.value != $("#password").val()) {
		confirmPassword.setCustomValidity("Passwords do not match!");
	} else {
		confirmPassword.setCustomValidity("");
	}
}