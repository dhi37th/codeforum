
$(function(){
var urlHref = window.location.href;
if(urlHref.indexOf('?id=') > -1){
  var url = new URL(urlHref);
  var questionId = url.searchParams.get("id");
  getQuestion(questionId);
}
});

function getQuestion(questionId){
$.ajax({
  		type: 'GET',
  		url: 'api/questions/'+questionId,
  		dataType: 'json',
  		success: function(question) {
  			createQuestionSkeleton(question);
  		},
  		error : function(xhr, textStatus, errorThrown){
  			if(xhr.status == 404){

  			}else  if(xhr.status == 401 || xhr.status == 403){
  				alert('Unauthorized to access resource');
  			}else{
  				alert('Error fetching questions');
  			}
  		},
  		async: true
  	});
}

function getCommentsForQuestion(questionId){
$.ajax({
  		type: 'GET',
  		url: 'api/questions/'+questionId+'/comments',
  		dataType: 'json',
  		success: function(questionComments) {
  			createQuestionCommentBody(questionComments);
  		},
  		error : function(xhr, textStatus, errorThrown){
  			if(xhr.status == 404){

  			}else  if(xhr.status == 401 || xhr.status == 403){
  				alert('Unauthorized to access resource');
  			}else{
  				alert('Error fetching questions');
  			}
  		},
  		async: true
  	});
}

function getAnswersForQuestions(questionId){
$.ajax({
  		type: 'GET',
  		url: 'api/questions/'+questionId+'/answers',
  		dataType: 'json',
  		success: function(answers) {
  			createAnswerBody(questionId,answers);
  		},
  		error : function(xhr, textStatus, errorThrown){
  			if(xhr.status == 404){

  			}else  if(xhr.status == 401 || xhr.status == 403){
  				alert('Unauthorized to access resource');
  			}else{
  				alert('Error fetching questions');
  			}
  		},
  		async: true
  	});
}

function getCommentsForAnswer(questionId,answerId){
$.ajax({
  		type: 'GET',
  		url: 'api/questions/'+questionId+'/answers/'+answerId+'/comments',
  		dataType: 'json',
  		success: function(answerComments) {
  			createAnswerCommentBody(answerComments,answerId);
  		},
  		error : function(xhr, textStatus, errorThrown){
  			if(xhr.status == 404){

  			}else  if(xhr.status == 401 || xhr.status == 403){
  				alert('Unauthorized to access resource');
  			}else{
  				alert('Error fetching questions');
  			}
  		},
  		async: true
  	});
}


function createQuestionSkeleton(question){
    createQuestionHeader(question);
    createQuestionBody(question);
    createQuestionCommentSkeleton(question);
    createAnswersSkeleton(question);
}

function createQuestionHeader(question){
console.log(question);
var quesHeaderDiv=
"<div class='row px-3 pt-3' data-question='"+question.id+"'>"+
  "<div class='col-12'>"+
    "<div class='row'>"+
      "<h1>"+question.heading+"</h1>"+
    "</div>"+
    "<div class='row mb-2'>"+
      "<h6><span class='text-muted'>Asked&nbsp</span><span>"+question.createdAt+"</span></h6>"+
      "<h6><span class='text-muted ml-4'>Updated </span><span>"+question.updatedAt+"</span></h6>"+
    "</div>"+
  "</div>"+
"</div>"+
"<hr>"
$('#qD').append(quesHeaderDiv);
}

function createQuestionBody(question){
var questionBodyDiv=
"<div class='row'>"+
"  <div class='col-2'>"+
"    <div class='row m-1'>"+
"      <div class='col-md-12'>"+
"        <button onclick='increaseQuestionUpvote(1)'>"+
"          <svg xmlns='http://www.w3.org/2000/svg' width='20' height='20'>"+
"            <polygon points='2,18 18,18 10,4'></polygon>"+
"          </svg>"+
"        </button>"+
"      </div>"+
"    </div>"+
"    <div class='row m-1'>"+
"      <div class='col-md-12'><span id='questionUpVotes'>"+question.upVote+"</span></div>"+
"    </div>"+
"    <div class='row m-1'>"+
"      <div class='col-md-12'>"+
"        <button onclick='increaseQuestionUpvote(-1)'>"+
"          <svg xmlns='http://www.w3.org/2000/svg' width='20' height='20'>"+
"            <polygon points='2,4 18,4 10,18'></polygon>"+
"          </svg>"+
"        </button>"+
"      </div>"+
"    </div>"+
"  </div>"+
"  <div class='col-10'>"+
"    <div class='row'>"+
"      <p class='font-xlarge'>"+question.text+"</p>"+
"    </div>"+
"    <div class='row mr-1 pb-2 border-bottom' id='questionTagRow'>"+
"      <div class='col-8'>"+
"       "+createQuestionTag(question.tag)+""+
"      </div>"+
"      <div class='col-4'>"+
"        <p class='font-weight-bold float-right'>-&nbsp"+question.createdBy+"</p>"+
"      </div>"+
"    </div>"+
"    <div class='row'>"+
"      <ul class='commentList w-100 mr-3' id='questionComments'>"+
"      </ul>"+
"      <ul class='commentList w-100 mr-3' id='addQuestionComment'>"+
"        <li class='border-bottom w-100 lh-2 mb-2' id='newCommentLi' style='display:none;'>"+
"          <div class='row'>"+
"            <div class='col-12'>"+
"             <form onsubmit='addNewQuestionComment("+question.id+", event)'>"+
"              <textarea required class='new-comment-textarea' row='3'></textarea>"+
"              <input class='btn btn-primary' style='line-height:15px;' type='submit' value='Add'>"+
"              <a class='btn' onclick='$(newCommentLi).hide();'>Cancel</a>"+
"             </form>"+
"            </div>"+
"          </div>"+
"        </li>"+
"        <li><a onclick='$(newCommentLi).show();' class='a-link text-muted'>add comment</a></li>"+
"      </ul>"+
"    </div>"+
"  </div>"+
"</div>"

$('#qD').append(questionBodyDiv);
}

function createQuestionCommentSkeleton(question){
    getCommentsForQuestion(question.id);
}

function createQuestionCommentBody(commentList){
  $('#questionComments').empty();
  commentList.forEach(comment=>{
    var commentBody =
    "        <li class='border-bottom w-100 lh-2' data-question-comment='"+comment.id+"'>"+
    "          <div class='row'>"+
    "            <div class='col-1'>"+
    "              <button onclick='incrementComment()' title='Up Vote?'>"+
    "                &#10003;"+
    "              </button>"+
    "              <span class='font-medium'>"+comment.upVote+"</span>"+
    "            </div>"+
    "            <div class='col-10'>"+
    "              <span class='font-large'>"+comment.text+"</span>"+
    "                <span class='font-weight-bold'>&nbsp-"+comment.createdBy+"</span><span class='text-muted'>&nbsp "+comment.createdAt+"</span>"+
    "            </div>"+
    "          </div>"+
    "        </li>";
    $('#questionComments').append(commentBody);
  });
}

function addNewQuestionComment(questionId,event){
  event.preventDefault();
  var id="#newCommentLi";
  var text = $(id).find("textarea").val();
  $.ajax({
    		type: 'POST',
    		url: 'api/questions/'+questionId+'/comments',
    		contentType: "application/json",
    		data: JSON.stringify({ text : text}),
    		dataType: 'json',
    		success: function(comment) {
    		  $(id).find("textarea").val('');
    		  $(id).hide();
    			getCommentsForQuestion(questionId);
    		},
    		error : function(xhr, textStatus, errorThrown){
    			if(xhr.status == 404){

    			}else  if(xhr.status == 401 || xhr.status == 403){
    				alert('Unauthorized to access resource');
    			}else{
    				alert('Error fetching questions');
    			}
    		},
    		async: true
    	});
}

function createAnswersSkeleton(question){
  getAnswersForQuestions(question.id);
}

function createAnswerBody(questionId,answers){
  var answerHeader =
  "<div class='row'>"+
   "<div class='col-12'>"+
    "<h3>"+answers.length+" Answers</h3>"+
   "</div>"+
  "</div>"+
  "<hr>";
  $('#qD').append(answerHeader);
  answers.forEach(answer=> {
       var answerDiv =
       "<div class='row' data-answer='"+answer.id+"'>"+
       "  <div class='col-2'>"+
       "    <div class='row m-1'>"+
       "      <div class='col-md-12'>"+
       "        <button onclick='increaseUpvote(1)'>"+
       "          <svg xmlns='http://www.w3.org/2000/svg' width='20' height='20'>"+
       "            <polygon points='2,18 18,18 10,4'></polygon>"+
       "          </svg>"+
       "        </button>"+
       "      </div>"+
       "    </div>"+
       "    <div class='row m-1'>"+
       "      <div class='col-md-12'><span>"+answer.upVote+"</span></div>"+
       "    </div>"+
       "    <div class='row m-1'>"+
       "      <div class='col-md-12'>"+
       "        <button onclick='increaseUpvote(-1)'>"+
       "          <svg xmlns='http://www.w3.org/2000/svg' width='20' height='20'>"+
       "            <polygon points='2,4 18,4 10,18'></polygon>"+
       "          </svg>"+
       "        </button>"+
       "      </div>"+
       "    </div>"+
       "  </div>"+
       "  <div class='col-10'>"+
       "    <div class='row'>"+
       "      <span class='font-xlarge'>"+answer.text+"</span>"+
       "    </div>"+
       "    <div class='row mr-1 pb-2 border-bottom'>"+
       "      <div class='col-8'>"+
       "      </div>"+
       "      <div class='col-4'>"+
       "        <p class='font-weight-bold float-right'>-&nbsp"+answer.createdBy+"</p>"+
       "      </div>"+
       "    </div>"+
       "    <div class='row'>"+
       "      <ul class='commentList w-100 mr-3' id='answerComments'>"+
       "      </ul>"+
       "      <ul class='commentList w-100 mr-3'>"+
       "        <li class='border-bottom w-100 lh-2 mb-2' id='aCom"+answer.id+"' style='display:none;'>"+
       "          <div class='row'>"+
       "            <div class='col-12'>"+
       "             <form onsubmit='addNewAnswerComment("+answer.id+","+questionId+", event)'>"+
       "              <textarea required class='new-comment-textarea' row='3'></textarea>"+
       "              <input class='btn btn-primary' style='line-height:15px;' type='submit' value='Add'>"+
       "              <a class='btn' onclick='$(aCom"+answer.id+").hide();'>Cancel</a>"+
       "             </form>"+
       "            </div>"+
       "          </div>"+
       "        </li>"+
       "        <li><a onclick='$(aCom"+answer.id+").show();' class='a-link text-muted'>add comment</a></li>"+
       "      </ul>"+
       "    </div>"+
       "  </div>"+
       "</div>"+
       "<hr>"
       $('#qD').append(answerDiv);
       getCommentsForAnswer(questionId,answer.id);
  });
  createNewAnswerSkeleton(questionId);
}
function createAnswerCommentBody(comments,answerId){
  $("div").find("[data-answer='"+answerId+"']").find("#answerComments").empty();
  comments.forEach(comment=>{
    var commentBody =
    "        <li class='border-bottom w-100 lh-2' data-answer-comment='"+comment.id+"'>"+
    "          <div class='row '>"+
    "            <div class='col-1'>"+
    "              <button onclick='incrementComment()' title='Up Vote?'>"+
    "                &#10003;"+
    "              </button>"+
    "              <span class='font-medium'>"+comment.upVote+"</span>"+
    "            </div>"+
    "            <div class='col-10'>"+
    "              <span class='font-large'>"+comment.text+"</span><span"+
    "                class='font-weight-bold'>&nbsp-"+comment.createdBy+"</span><span class='text-muted'>&nbsp "+comment.createdAt+"</span>"+
    "            </div>"+
    "          </div>"+
    "        </li>";
    $("div").find("[data-answer='"+answerId+"']").find("#answerComments").append(commentBody);

  });
}

function addNewAnswerComment(answerId,questionId,event){
  var id = "#aCom"+answerId;
  event.preventDefault();
  var text = $(id).find("textarea").val();
  $.ajax({
      		type: 'POST',
      		url: 'api/questions/'+questionId+'/answers/'+answerId+'/comments',
      		contentType: "application/json",
      		data: JSON.stringify({ text : text}),
      		dataType: 'json',
      		success: function(comment) {
      		  $(id).find("textarea").val('');
      		  $(id).hide();
      			getCommentsForAnswer(questionId,answerId);
      		},
      		error : function(xhr, textStatus, errorThrown){
      			if(xhr.status == 404){

      			}else  if(xhr.status == 401 || xhr.status == 403){
      				alert('Unauthorized to access resource');
      			}else{
      				alert('Error fetching questions');
      			}
      		},
      		async: true
      	});
}

function createNewAnswerSkeleton(questionId){
  var body =
      "<div class='row'>"+
         "<div class='col-12'>"+
          "<h3>Your Answer</h3>"+
         "</div>"+
        "</div>"+
        "<hr>"+
      "<div class='row'>"+
        "<div class='col-12'>"+
         "<textarea name='newAnswer' id='newAnswer'></textarea>"+
         "<a id='submitAnswer' onclick='addNewAnswer("+questionId+")' class='btn btn-primary my-3 p-2'>Submit</a>"+
        "</div>"+
      "</div>";
      $('#qD').append(body);

      CKEDITOR.editorConfig = function (config) {
            config.language = 'es';
            config.uiColor = '#F7B42C';
            config.height = 300;
            config.toolbarCanCollapse = true;

      };
      CKEDITOR.replace('newAnswer');
}

function addNewAnswer(questionId){
  var text = CKEDITOR.instances['newAnswer'].getData();
   $.ajax({
        		type: 'POST',
        		url: 'api/questions/'+questionId+'/answers/',
        		contentType: "application/json",
        		data: JSON.stringify({ text : text}),
        		dataType: 'json',
        		success: function(comment) {
        		  window.location.reload(true);
        		},
        		error : function(xhr, textStatus, errorThrown){
        			if(xhr.status == 404){

        			}else  if(xhr.status == 401 || xhr.status == 403){
        				alert('Unauthorized to access resource');
        			}else{
        				alert('Error fetching questions');
        			}
        		},
        		async: true
        	});

}

function createQuestionTag(tag){
  var tagHtml = '';
  var chars = tag.split(',');
  chars.forEach(t => {
     if(t!=undefined || t!='null'){
       tagHtml+="<a class='tag-item m-1 p-1'>"+t+"</a>"
     }
   });
  return tagHtml;
}


function increaseUpvote(incrementor){
  var voteValue = parseInt($('#questionUpVotes').text());
  voteValue += incrementor;
  $('#questionUpVotes').text(voteValue);
}

function increaseQuestionUpvote(upVoteBy){

}