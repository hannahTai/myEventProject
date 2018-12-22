// datetimepicker
$("#evetit_startdate").datetimepicker({
	format:'Y-m-d',
	timepicker: false,
	minDate:new Date(),
	onShow:function(){
		this.setOptions({maxDate: $("#evetit_enddate").val() ? $("#evetit_enddate").val() : false})}
});
	 
$("#evetit_enddate").datetimepicker({
	format:'Y-m-d',
	timepicker: false,
	onShow:function(){
		this.setOptions({minDate:$("#evetit_startdate").val() ? $("#evetit_startdate").val() : false})}
});