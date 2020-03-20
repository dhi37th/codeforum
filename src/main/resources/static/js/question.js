var allQuestionArray = [];

$(function(){

});

function increaseUpvote(incrementor){
  var voteValue = parseInt($('#questionUpVotes').text());
  voteValue += incrementor;
  $('#questionUpVotes').text(voteValue);
}

function createQuestionSkeleton(){
    if(!allQuestionArray.length > 0) return;
    allQuestionArray.forEach((question) => {
          createQuestionDiv(question);
    });
}
function createQuestionDiv(question){
  //$('#questionColumn').empty();
  $('#noOfQuestions').html(allQuestionArray.length);
  var questionRow =
  "<div class='row border-bottom border-left border-dark shadow p-3 mb-3 bg-white rounded'>"+
    "<div class='col-2' id='stats'>"+
      "<div class='row'>"+
        "<div class='col-md-12'><strong id='questionUpVotes'>"+question.upVote+"</strong></div>"+
        "<div class='col-md-12'><small>votes</small></div>"+
      "</div>"+
      "<div class='row'>"+
        "<div class='col-md-12'><strong id='questionAnswers'>"+0+"</strong></div>"+
        "<div class='col-md-12'><small>answers</small></div>"+
      "</div>"+
    "</div>"+
    "<div class='col-10'>"+
      "<div class='row'>"+
        "<h4><a href=' id='questionHeading'>"+question.heading+"</a></h4>"+
      "</div>"+
      "<div class='row'>"+
        "<p id='questionIssue' class='text-truncate'>"+question.issue+"</p>"+
      "</div>"+
      "<div class='row'>"+
        "<div class='col-8'>"+
          "<p>"+createQuestionTag(question.tag)+"</p>"+
        "</div>"+
        "<div class='col-4'>"+
          "<p class='mb-1 small'>asked "+question.updatedAt+"</p>"+
          "<p class='mb-1'>User</p>"+
        "</div>"+
      "</div>"+
    "</div>"+
  "</div>";
  $('#questionColumn').append(questionRow);
}

function createQuestionTag(tag){
  var tagHtml = '';
  var chars = tag.split(',');
  chars.forEach(t => {
     if(t!=undefined || t!='null'){
      console.log(t);
       tagHtml+="<small class='tag-item mr-2 p-1'>"+t+"</small>"
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
  			allQuestionArray = questionArr;
  			createQuestionSkeleton();
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

function getQuestion(questionId){

}