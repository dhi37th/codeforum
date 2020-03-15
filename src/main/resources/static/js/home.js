$(function(){
$('#leftNav a').click(function(e){
  e.preventDefault();
  if($(this).hasClass('active')) return; // if already active return

  $('.nav-link').removeClass('active');
  $(this).addClass('active');
  console.log('clicked '+this.id);
});

$('#sidebarToggle').click(function(e){
  e.preventDefault();
  console.log('sidebar clicked');
});

$('#searchButtonToggle').click(function(e){
  e.preventDefault();
  console.log('search clicked');
});
});

function showAlertModal(message){
	var mod = $('#alertModal');
	mod.modal('show');

	mod.find('#alertMessage').text(message);

	mod.on('hidden.bs.modal', function () {
		mod.find('#alertMessage').text('');
	});
}