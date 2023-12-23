var buttonLoad4Cities;
var dropDownCountry4Cities;
var dropDownStates4Cities;
var dropDownCities;
var buttonAddCity;
var buttonDeleteCity;
var buttonUpdateCity;
var labelCityName;
var fieldCityName;

$(document).ready(function() {
	buttonLoad4Cities = $("#buttonLoadCountriesForCities");
	dropDownCountry4Cities = $("#dropDownCountriesForCities");
	dropDownStates4Cities = $("#dropDownStatesForCities");
	dropDownCities = $("#dropDownCities");
	buttonAddCity = $("#buttonAddCity");
	buttonUpdateCity = $("#buttonUpdateCity");
	buttonDeleteCity = $("#buttonDeleteCity");
	labelCityName = $("#labelCityName");
	fieldCityName = $("#fieldCityName");
	
	buttonLoad4Cities.click(function() {
		loadCountries4Cities();
	});
	
	dropDownCountry4Cities.on("change", function() {
		loadStates4Country();
	});

	dropDownStates4Cities.on("change", function() {
		//changeFormStateToSelectedState();
		loadCities4State();
	});
	
	dropDownCities.on("change",function(){
		changeFormCityToSelectedCity();	
	});
		
	buttonAddCity.click(function() {
		if (buttonAddCity.val() == "Add") {
			addCity();
		} else {
			//changeFormStateToNew();
			changeFormCityToNew();
		}
	});
	
	buttonUpdateCity.click(function() {
		updateCity();
	});
	
	buttonDeleteCity.click(function() {
		deleteCity();
	});
});

function deleteCity() {
	cityId = dropDownCities.val();
	
	url = contextPath + "cities/delete/" + cityId;
	
	$.ajax({
		type: 'DELETE',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function() {
		$("#dropDownCities option[value='" + cityId + "']").remove();
		//changeFormStateToNew();
		changeFormCityToNew();
		showToastMessage("The city has been deleted");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});		
}

function updateCity() {
	if (!validateFormCity()) return;
	
	url = contextPath + "cities/save";
	cityId = dropDownCities.val();
	cityName = fieldCityName.val();
	
	selectedState = $("#dropDownStatesForCities option:selected");
	stateId = selectedState.val();
	stateName = selectedState.text();
	
	jsonData = {id: cityId, name: cityName, state: {id: stateId, name: stateName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(cityId) {
		$("#dropDownCities option:selected").text(cityName);
		showToastMessage("The city has been updated");
		//changeFormStateToNew();
		changeFormCityToNew();
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});	
}

function addCity() {
	if (!validateFormCity()) return;
	
	url = contextPath + "cities/save";
	cityName = fieldCityName.val();
	
	selectedState = $("#dropDownStatesForCities option:selected");
	stateId = selectedState.val();
	stateName = selectedState.text();
	
	jsonData = {name: cityName, state: {id: stateId, name: stateName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(cityId) {
		selectNewlyAddedCity(cityId, cityName);
		showToastMessage("The new city has been added");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
		
}

/*function validateFormState() {
	formState = document.getElementById("formState");
	if (!formState.checkValidity()) {
		formState.reportValidity();
		return false;
	}	
	
	return true;
}*/

function validateFormCity() {
	formCity = document.getElementById("formCity");
	if (!formCity.checkValidity()) {
		formCity.reportValidity();
		return false;
	}	
	
	return true;
}



function selectNewlyAddedCity(cityId, cityName) {
	$("<option>").val(cityId).text(cityName).appendTo(dropDownCities);
	
	$("#dropDownCities option[value='" + cityId + "']").prop("selected", true);
	
	fieldCityName.val("").focus();
}


function changeFormCityToNew() {
	buttonAddCity.val("Add");
	fieldCityName.text("City/District Name:");
	
	buttonUpdateCity.prop("disabled", true);
	buttonDeleteCity.prop("disabled", true);
	
	fieldCityName.val("").focus();	
}


/*function changeFormStateToSelectedState() {
	buttonAddCity.prop("value", "New");
	buttonUpdateCity.prop("disabled", false);
	buttonUpdateCity.prop("disabled", false);
	
	fieldCityName.text("Selected State/Province:");
	
	selectedStateName = $("#dropDownStates4Cities option:selected").text();
	fieldCityName.val(selectedStateName);
	
}*/

function changeFormCityToSelectedCity() {
	buttonAddCity.prop("value", "New");
	buttonUpdateCity.prop("disabled", false);
	buttonDeleteCity.prop("disabled", false);
	
	fieldCityName.text("Selected City/District:");
	
	selectedCityName = $("#dropDownCities option:selected").text();
	fieldCityName.val(selectedCityName);
	
}

function loadStates4Country() {
	selectedCountry = $("#dropDownCountriesForCities option:selected");
	countryId = selectedCountry.val();
	url = contextPath + "states/list_by_country/" + countryId;
	
	$.get(url, function(responseJSON) {
		dropDownStates4Cities.empty();
		
		$("<option>").val(-1).text("-- Select State --").appendTo(dropDownStates4Cities);
		$.each(responseJSON, function(index, state) {
			$("<option>").val(state.id).text(state.name).appendTo(dropDownStates4Cities);
		});
		
	}).done(function() {
		//changeFormStateToNew();
		showToastMessage("All states have been loaded for country " + selectedCountry.text());
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});	
}

function loadCountries4Cities() {
	url = contextPath + "countries/list";
	$.get(url, function(responseJSON) {
		dropDownCountry4Cities.empty();
		
		$("<option>").val(-1).text("-- Select Country--").appendTo(dropDownCountry4Cities);
		
		$.each(responseJSON, function(index, country) {
			$("<option>").val(country.id).text(country.name).appendTo(dropDownCountry4Cities);
		});
		
	}).done(function() {
		buttonLoad4Cities.val("Refresh Country List");
		showToastMessage("All countries have been loaded");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
}


function loadCities4State(){
	selectedState = $("#dropDownStatesForCities option:selected");
	stateId = selectedState.val();
	url=contextPath + "cities/list_by_state/" + stateId;
	
	$.get(url, function(responseJSON) {
		dropDownCities.empty();
		
		$.each(responseJSON, function(index, city) {
			$("<option>").val(city.id).text(city.name).appendTo(dropDownCities);
		});
		
	}).done(function() {
		//changeFormStateToNew();
		changeFormCityToNew();
		showToastMessage("All Cities have been loaded for state " + selectedState.text());
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});	
}










