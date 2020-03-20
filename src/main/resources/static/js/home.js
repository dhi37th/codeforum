$(function(){
//Get all questions for home
getAllQuestions();

});

function showAlertModal(message){
	var mod = $('#alertModal');
	mod.modal('show');

	mod.find('#alertMessage').text(message);

	mod.on('hidden.bs.modal', function () {
		mod.find('#alertMessage').text('');
	});
}