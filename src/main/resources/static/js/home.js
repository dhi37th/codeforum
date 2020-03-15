$(function(){
$('#leftNav a').click(function(e){
  e.preventDefault();
  if($(this).hasClass('active')) return; // if already active return

  $('.nav-link').removeClass('active');
  $(this).addClass('active');
  console.log('clicked '+this.id);
});
});