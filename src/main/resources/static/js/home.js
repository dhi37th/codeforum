$(function(){
//Get all questions for home
getAllQuestions();
});
function createAllQuestionSkeleton(allQuestionArray){
  //$('#questionColumn').empty();
  $('#noOfQuestions').html(allQuestionArray.length);
    allQuestionArray.forEach((question) => {
          createAllQuestionDiv(question);
    });
}
function createAllQuestionDiv(question){
  var questionRow =
  "<div class='row border-bottom border-left border-dark shadow p-3 mb-3 bg-white rounded'>"+
    "<div class='col-2' id='stats'>"+
      "<div class='row'>"+
        "<div class='col-md-12'><strong>"+question.upVote+"</strong></div>"+
        "<div class='col-md-12'><small>votes</small></div>"+
      "</div>"+
      "<div class='row'>"+
        "<div class='col-md-12'><strong id='questionAnswers'>"+0+"</strong></div>"+
        "<div class='col-md-12'><small>answers</small></div>"+
      "</div>"+
    "</div>"+
    "<div class='col-10'>"+
      "<div class='row'>"+
        "<h4><a href='question?id="+question.id+"'><span class='font-xxlarger'>"+question.heading+"</span></a></h4>"+
      "</div>"+
      "<div class='row'>"+
        "<span class='text-truncate mr-5 font-xlarge'>"+question.text+"</span>"+
      "</div>"+
      "<div class='row'>"+
        "<div class='col-8 ml-n1_2'>"+
          ""+createAllQuestionTag(question.tag)+""+
        "</div>"+
        "<div class='col-4'>"+
          "<p class='mb-1 text-muted'>asked "+question.createdAt+"</p>"+
          "<p class='mb-1 font-large'>User</p>"+
        "</div>"+
      "</div>"+
    "</div>"+
  "</div>";
  $('#questionColumn').append(questionRow);
}

function createAllQuestionTag(tag){
  if(tag==undefined || tag==null || tag=='null') return;
  var tagHtml = '';
  var chars = tag.split(',');
  chars.forEach(t => {
     if(t!=undefined || t!='null'){
       tagHtml+="<a class='tag-item m-1 p-1 small'>"+t+"</a>"
     }
   });
  return tagHtml;
}
function getAllQuestions(){
  $.ajax({
  		type: 'GET',
  		url: 'api/questions/all',
  		dataType: 'json',
  		success: function(questionArr) {
  			createAllQuestionSkeleton(questionArr);
  		},
  		error : function(xhr, textStatus, errorThrown){
  			if(xhr.status == 404){
  			  $('#noOfQuestions').html('0');
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

function showAlertModal(message){
	var mod = $('#alertModal');
	mod.modal('show');

	mod.find('#alertMessage').text(message);

	mod.on('hidden.bs.modal', function () {
		mod.find('#alertMessage').text('');
	});
}