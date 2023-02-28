/**
 * 
 */

let deletedExtraImages = new Map();
$(document).ready(function() {

	/*
			$("input[name='extraImage']").each(function(index) {
		
				$(this).change(function() {
					if (!checkFileSize(this)) {
						return;
					}
					showExtraImageThumbnail(this, index);
				});
			});
		
			$("a[name='linkRemoveExtraImage']").each(function(index) {
				$(this).click(function() {
					removeExtraImage(index);
				});
			});
			
		
		*/


	$("input[name='extraImage']").each(function(index) {
		$(this).change(function() {
			if (!checkFileSize(this)) {
				return;
			}
			showExtraImageThumbNail(this, index + 1);
		});

	});

	$("a[name='linkRemoveExtraImage']").each(function(index) {
		$(this).click(function() {
			removeExtraImage(index + 1);
		});
	});




});


function showExtraImageThumbNail(fileInput, index) {
	//alert(" I am back..........");
	var file = fileInput.files[0];
	var reader = new FileReader();

	reader.onload = function(e) {
		$("#extraThumbnail" + index).attr("src", e.target.result);
	}
	reader.readAsDataURL(file);
	addNextExtraImageSection(index + 1);
}


function addNextExtraImageSection(index) {


	htmlExtraImage = `
	<div class="col border m-3 p-3" id="divExtraImage${index}">
				<div id="extraImageHeader${index}">
					<label>Extra Image #${index}:</label>
				</div>
				 
				<div class="m-2">
					<img id="extraThumbnail${index}" class="image-fluid"
						alt="Extra  Image ${index} Preview"
						src="${defaultImageThumbnailSrc}" width="150" height="150" />
				</div>
				<div>
					<input type="file" name="extraImage" id="extraImage${index}"
					onchange="showExtraImageThumbNail(this,${index})"
						accept="image/png,image/jpeg" />
				</div>

			</div>
	`;

	htmlLinkRemove = `
		<a class="btn fas fa-times-circle fa-2x icon-dark float-right" id="closeExtraImage${index - 1}"
		href="javascript:removeExtraImage(${index - 1})"
		title="Remove this Image">
		</a>
	`;


	if (!$(`input[id="extraImage${index}"]`)[0] && !deletedExtraImages.get(index)) {
		$("#divProductImages").append(htmlExtraImage);
	}

	//$("#divProductImages").append(htmlExtraImage);
	$("#closeExtraImage" + (index - 1)).remove();
	$("#extraImageHeader" + (index - 1)).append(htmlLinkRemove);
}


function removeExtraImage(index) {
	deletedExtraImages.set(index, true);
	$("#divExtraImage" + index).remove();

}