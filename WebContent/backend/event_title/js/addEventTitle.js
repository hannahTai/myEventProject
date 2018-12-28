// datetimepicker
$.datetimepicker.setLocale('zh');

$("#evetit_startdate").datetimepicker({
	format: 'Y-m-d',
	minDate: 0,
	timepicker: false,
    changeMonth: true,
    changeYear: true,	
	onShow: function(){
		this.setOptions({maxDate: $("#evetit_enddate").val() ? $("#evetit_enddate").val() : false})}
});
	 
$("#evetit_enddate").datetimepicker({
	format: 'Y-m-d',
	minDate: 0,
	timepicker: false,
    changeMonth: true,
    changeYear: true,
	onShow:function(){
		this.setOptions({minDate:$("#evetit_startdate").val() ? $("#evetit_startdate").val() : false})}
});

 $("#launchdate").datetimepicker({
	format:'Y-m-d',
	minDate: 0,
	timepicker: false,
    changeMonth: true,
    changeYear: true,
	onShow:function(){
		this.setOptions({maxDate:$("#offdate").val() ? $("#offdate").val() : false})},
 });
 
 $("#offdate").datetimepicker({
	format:'Y-m-d',
	minDate: 0,
	timepicker: false,
    changeMonth: true,
    changeYear: true,
	onShow:function(){
		this.setOptions({minDate:$("#launchdate").val() ? $("#launchdate").val() : false})},
 });



function imagesPreview(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $("#evetit_poster_preview").attr("src", e.target.result);           
        }        
        reader.readAsDataURL(input.files[0]);
    }
}