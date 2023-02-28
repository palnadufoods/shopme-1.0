$(document)
	.ready(
		function() {
			$("#fileImage")
				.change(
					function() {

						if (checkFileSize(this)) { showImageThumbNail(this); }

					})

		});


function checkFileSize(fileInput) {

	fileSize = fileInput.files[0].size;
	str = fileInput.files[0].name;

	if (fileSize > MAX_FILE_SIZE
		|| str.length > 127) {

		fileInput
			.setCustomValidity("File Size  Should be less than " + MAX_FILE_SIZE + "KB OR File Name Should be less than 127 Charecters.");
		fileInput.reportValidity();
		return false;
	} else {
		fileInput
			.setCustomValidity("");

		return true;
	}

}

function showImageThumbNail(fileInput) {
	var file = fileInput.files[0];
	var reader = new FileReader();

	reader.onload = function(e) {
		$("#thumbnail").attr("src", e.target.result);
	}

	reader.readAsDataURL(file);
}


function showModalDialog(title, message) {

	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}