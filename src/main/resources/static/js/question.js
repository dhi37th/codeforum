
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

function getCommentsForQuestion(question){
$.ajax({
  		type: 'GET',
  		url: 'api/questions/'+question.id+'/comments/',
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

function getAnswersForQuestions(question){
$.ajax({
  		type: 'GET',
  		url: 'api/questions/'+question.id+'/answers',
  		dataType: 'json',
  		success: function(answers) {
  			createAnswerBody(question,answers);
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

function getCommentsForAnswer(question,answer){
$.ajax({
  		type: 'GET',
  		url: 'api/questions/'+question.id+'/answers/'+answer.id+'/comments/',
  		dataType: 'json',
  		success: function(answerComments) {
  			createAnswerCommentBody(answerComments,answer);
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
"      <p>"+question.text+"</p>"+
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
"    </div>"+
"  </div>"+
"</div>"

$('#qD').append(questionBodyDiv);
}

function createQuestionCommentSkeleton(question){
    getCommentsForQuestion(question);
}

function createQuestionCommentBody(commentList){
  commentList.forEach(comment=>{
    var commentBody =
    "        <li class='border-bottom w-100 lh-2' data-comment='"+comment.id+"'>"+
    "          <div class='row'>"+
    "            <div class='col-1'>"+
    "              <button class='btn-comment' onclick='incrementComment()' title='Up Vote?'>"+
    "                &#10003;"+
    "              </button>"+
    "              <span>"+comment.upVote+"</span>"+
    "            </div>"+
    "            <div class='col-10'>"+
    "              <span>"+comment.text+"</span>"+
    "                <span class='font-weight-bold'>&nbsp-"+comment.createdBy+"</span><span class='text-muted'>&nbsp "+comment.createdAt+"</span>"+
    "            </div>"+
    "          </div>"+
    "        </li>";
    $('#questionComments').append(commentBody);
  });
}

function createAnswersSkeleton(question){
  getAnswersForQuestions(question);
}

function createAnswerBody(question,answers){
  var answerHeader =
  "<div class='row'>"+
   "<div class='col-12'>"+
    "<h5>"+answers.length+" Answers</h5>"+
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
       "      <p>"+answer.text+"</p>"+
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
       "    </div>"+
       "  </div>"+
       "</div>"+
       "<hr>"
       $('#qD').append(answerDiv);

       getCommentsForAnswer(question,answer);
  });
}
function createAnswerCommentBody(comments,answer){
  comments.forEach(comment=>{
    var commentBody =
    "        <li class='border-bottom w-100 lh-2' data-comment='"+comment.id+"'>"+
    "          <div class='row '>"+
    "            <div class='col-1'>"+
    "              <button class='btn-comment' onclick='incrementComment()' title='Up Vote?'>"+
    "                &#10003;"+
    "              </button>"+
    "              <span>"+comment.upVote+"</span>"+
    "            </div>"+
    "            <div class='col-10'>"+
    "              <span>"+comment.text+"</span><span"+
    "                class='font-weight-bold'>&nbsp-"+comment.createdBy+"</span><span class='text-muted'>&nbsp "+comment.createdAt+"</span>"+
    "            </div>"+
    "          </div>"+
    "        </li>";
    $("div").find("[data-answer='"+answer.id+"']").find("#answerComments").append(commentBody);

  });
}


function createQuestionTag(tag){
  var tagHtml = '';
  var chars = tag.split(',');
  chars.forEach(t => {
     if(t!=undefined || t!='null'){
       tagHtml+="<a class='tag-item m-1 p-1 small'>"+t+"</a>"
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