
$(function(){
var urlHref = window.location.href;
if(urlHref.indexOf('?id=') > -1){
  var url = new URL(urlHref);
  var questionId = url.searchParams.get("id");
  getQuestion(questionId);
}
});

function increaseUpvote(incrementor){
  var voteValue = parseInt($('#questionUpVotes').text());
  voteValue += incrementor;
  $('#questionUpVotes').text(voteValue);
}

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
  				showAlertModal('Unauthorized to access resource');
  			}else{
  				showAlertModal('Error fetching questions');
  			}
  		},
  		async: true
  	});
}


function createQuestionSkeleton(question){
    createQuestionHeaderDiv(question);
    createQuestionBodyDiv(question)
}

function createQuestionHeaderDiv(question){
console.log(question);
var quesHeaderDiv=
"<div class='row px-3 pt-3'>"+
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

function createQuestionBodyDiv(question){
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
"    <div class='row' id='questionComments'>"+
"      <ul class='commentList w-100 mr-3'>"+
"        <li class='border-bottom w-100 lh-2' data-comment='1'>"+
"          <div class='row'>"+
"            <div class='col-1'>"+
"              <button class='btn-comment' onclick='incrementComment()' title='Up Vote?'>"+
"                &#10003;"+
"              </button>"+
"              <span>5</span>"+
"            </div>"+
"            <div class='col-10'>"+
"              <span>Are you sure that the information</span>"+
"                <span class='font-weight-bold'>&nbsp-Rol</span><span class='text-muted'>&nbsp 22-03-2020 11:20</span>"+
"            </div>"+
"          </div>"+
"        </li>"+
"        <li class='border-bottom w-100 lh-2' data-comment='2'>"+
"          <div class='row'>"+
"            <div class='col-1'>"+
"              <button class='btn-comment' onclick='incrementComment()' title='Up Vote?'>"+
"                &#10003;"+
"              </button>"+
"              <span>1</span>"+
"            </div>"+
"            <div class='col-10'>"+
"              <span>Yes</span><span class='font-weight-bold'>&nbsp-Ron</span><span"+
"                class='text-muted'>&nbsp 22-03-2020 11:20</span>"+
"            </div>"+
"          </div>"+
"        </li>"+
"      </ul>"+
"    </div>"+
"  </div>"+
"</div>"

$('#qD').append(questionBodyDiv);
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

function increaseQuestionUpvote(upVoteBy){

}