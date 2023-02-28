

dropdownBrands = $("#brand");
dropdownCategories = $("#category");
$(document).ready(function() {

	$("#shortDescription").richText();
	$("#fullDescription").richText();

	dropdownBrands.change(function() {
		//alert("brand change");
		dropdownCategories.empty();
		getCategories();
	});

	getCategoriesForNewForm();

});

function getCategoriesForNewForm() {
	catIdField = $("#categoryId");
	editMode = false;

	if (catIdField.length) {
		editMode = true;
	}

	if (!editMode) getCategories();
}



function getCategories() {
	//alert("getCat invoked");
	brandId = dropdownBrands.val();
	url = brandModuleURL + "/" + brandId + "/categories";

	$.get(url, function(responseJson) {
		//alert(responseJson);

		$.each(responseJson, function(index, category) {
			$("<option>").val(category.id).text(category.name)
				.appendTo(dropdownCategories);
		});
	});

	//alert(brandId);
}

function checkUnique(form) {
	productId = $("#id").val();
	productName = $("#name").val();

	csrfValue = $("input[name='_csrf']").val();


	params = {
		id: productId,
		name: productName,
		_csrf: csrfValue
	};
	$
		.post(
			checkUniqueUrl,
			params,
			function(response) {
				if (response == "OK") {
					form.submit();
				} else if (response == "Duplicate") {
					showModalDialog("Warning ",
						"There is another product having the name "
						+ productName);
				} else {
					//alert(response);
					showModalDialog("Error ",
						"There is Unknown response from the server");
				}
			}).fail(
				function() {
					showModalDialog("Error ",
						"Could not connect to the Server");
					console.log(url);
					console.log(params);


				})

	return false;
}