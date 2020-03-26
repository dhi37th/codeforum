$(function(){
createHeader();
createAskModal();
$('#sidebarToggle').click(function(e){
  e.preventDefault();
  //TODO: impmenet toggle for sidebar
  console.log('sidebar clicked');
});

$('#searchButtonToggle').click(function(e){
  e.preventDefault();
  //TODO: implement search feature
  console.log('search clicked');
});

//Change active class of sidebar button links except Ask Question link
$('#leftNav a').click(function(e){
  e.preventDefault();
  if($(this).hasClass('active') || $(this).hasClass('askQuestion')) return; // if already active return

  $('.nav-link').removeClass('active');
  $(this).addClass('active');
  console.log('clicked '+this.id);
});

});


function createHeader(){
var header =
"<nav class='navbar navbar-expand-lg sticky-top navbar-light bg-secondary' style='background-color: #A2F7F7;'>"+
  "<button class='navbar-toggler' type='button' data-toggle='collapse' id='sidebarToggle'>"+
    "<span class='navbar-toggler-icon'></span>"+
  "</button>"+
  "<a class='navbar-brand pr-5' href='home'>Code Forum</a>"+
  "<button class='navbar-toggler' type='button' data-toggle='collapse' id='searchButtonToggle'>"+
    "<svg class='search-icon' viewBox='0 0 24 24'>"+
      "<path d='M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 "+
      "3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 "+
      "14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z'/>"+
      "<path d='M0 0h24v24H0z' fill='none'/>"+
    "</svg>"+
  "</button>"+
  "<div class='navbar-collapse collapse'>"+
    "<form class='mx-4 my-auto d-inline w-100'>"+
      "<div class='input-group'>"+
        "<div class='input-group-prepend'>"+
          "<span class='input-group-text'>"+
            "<svg class='search-icon' viewBox='0 0 24 24'>"+
              "<path"+
                  "d='M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 "+
                  "13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 " +
                  "4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 "+
                  "11.99 14 9.5 14z'/>"+
              "<path d='M0 0h24v24H0z' fill='none'/>"+
            "</svg>"+
          "</span>"+
        "</div>"+
        "<input type='text' class='form-control border text-center' placeholder='Search'>"+
      "</div>"+
    "</form>"+
  "</div>"+
  "<ul class='navbar-nav'>"+
    "<li class='nav-item'>"+
      "<a href='logout' class='btn btn-dark border'>Logout</a>"+
    "</li>"+
  "</ul>"+
"</nav>"

$('body').prepend(header);

var sideNav =
"<div class='col-lg-2 col-md-1'>"+
"<nav class='navbar navbar-expand-lg sticky-top side-sticky'>"+
  "<div class='collapse navbar-collapse'>"+
    "<ul class='nav nav-pills flex-md-row' id='leftNav'>"+
      "<li class='nav-item mb-3 border-bottom border-dark'>"+
        "<a class='nav-link active' style='cursor:pointer;' id='allQuestionNav'>All Questions</a>"+
      "</li>"+
      "<li class='nav-item mb-3'>"+
        "<a class='nav-link' style='cursor:pointer;' id='myQuestionNav'>My Questions</a>"+
      "</li>"+
      "<li class='nav-item mb-3 mt-5'>"+
        "<a class='nav-link bg-success askQuestion' onclick='askQuestion();' style='cursor:pointer;' id='askQuestionNav'>Ask Question</a>"+
      "</li>"+
    "</ul>"+
  "</div>"+
"</nav>"+
"</div>";
$('.mainRow').prepend(sideNav);
}

function createAskModal(){
  var modal =
  "<div class='modal fade' id='askModal' tabindex='-1' role='dialog'>"+
    "<div class='modal-dialog modal-lg' role='document'>"+
      "<div class='modal-content'>"+
        "<div class='modal-body'>"+
          "<div class='row'>"+
             "<div class='col-12'>"+
              "<p style='color:green; display:none;' id='feedback'></p>"+
              "<div class='form-group'>"+
                "<label for='q-heading' class='control-label'>Heading:</label>"+
                "<input type='text' class='form-control' id='q-heading'	value=''>"+
              "</div>"+
              "<div class='form-group'>"+
                "<label for='q-text' class='control-label'>Text:</label>"+
                "<textarea  name='q-text' id='q-text'></textarea>"+
              "</div>"+
              "<div class='form-group'>"+
                "<label for='q-tag' class='control-label'>Tag:<small>(Enter comma seperated values)</small></label>"+
                "<input type='text' class='form-control' id='q-tag'>"+
              "</div>"+
              "</div>"+
          "</div>"+
        "</div>"+
        "<div class='modal-footer'>"+
          "<a class='btn btn-primary' id='askSubmit'>Submit</a>"+
          "<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>"+
        "</div>"+
      "</div>"+
    "</div>"+
  "</div>";

  $('body').append(modal);

  CKEDITOR.editorConfig = function (config) {
              config.language = 'es';
              config.uiColor = '#F7B42C';
              config.height = 300;
              config.toolbarCanCollapse = true;

        };
  CKEDITOR.replace('q-text');

  $('#askSubmit').click(function(e){
    e.preventDefault();
    var heading = $('#q-heading').val();
    var tag = $('#q-tag').val();
    var text = CKEDITOR.instances['q-text'].getData();

    $.ajax({
        		type: 'POST',
        		url: 'api/questions',
        		contentType: "application/json",
        		data: JSON.stringify({ heading : heading, text : text, tag : tag}),
        		dataType: 'json',
        		success: function(question) {
        		  $('#feedback').text('Question created successfully');
              $('#feedback').show();
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
  });
}

function askQuestion(){
  	var mod = $('#askModal');
  	mod.modal('show');

  	mod.on('hidden.bs.modal', function () {
  	  CKEDITOR.instances['q-text'].setData('');
  		mod.find('#q-heading').val('');
  		mod.find('#q-text').val('');
  		mod.find('#q-tag').val('');
  		mod.find('#feedback').hide();
  	});
}