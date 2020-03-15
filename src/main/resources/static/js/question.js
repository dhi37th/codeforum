var allQuestionArray = [];

$(function(){
    getQuestions();
});

function createQuestionSkeleton(){
    if(!allQuestionArray.length > 0) return;
    allQuestionArray.forEach((question) => {
          createQuestionDiv(question);
    });
}
function createQuestionDiv(question){

}
function getQuestions(){
  $.ajax({
  		type: 'GET',
  		url: 'api/questions/all',
  		dataType: 'json',
  		success: function(questionArr) {
  			allQuestionArray = questionArr;
  			createQuestionSkeleton();
  		},
  		error : function(xhr, textStatus, errorThrown){
  			if(xhr.status == 404){
  			  $('#noQuestion').html('No questions available. Click on ask question to get started');
  			  $('#noQuestion').show();
  				globalRecipeArray = {};
  			}else  if(xhr.status == 401 || xhr.status == 403){
  				showAlertModal('Unauthorized to access resource');
  			}else{
  				showAlertModal('Error fetching questions');
  			}
  		},
  		async: true
  	});
}

function getQuestion(questionId){

}