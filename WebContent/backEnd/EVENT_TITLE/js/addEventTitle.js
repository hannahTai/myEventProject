// datetimepicker
$("#evetit_startdate").datetimepicker({
	format:'Y-m-d H:i',
	timepicker:true,
	step: 30,
	onShow:function(){
		this.setOptions({maxDate: $("#evetit_enddate").val() ? $("#evetit_enddate").val() : false})}
});
	 
$("#evetit_enddate").datetimepicker({
	format:'Y-m-d H:i',
	timepicker:true,
	step: 30,
	onShow:function(){
		this.setOptions({minDate:$("#evetit_startdate").val() ? $("#evetit_startdate").val() : false})}
});

// $("#launchdate").datepicker({
//     defaultDate: "+1w",
//     changeMonth: true,
//     changeYear: true,
//     dateFormat: "yy-mm-dd",
//     timepicker: false,
//     minDate: 0,
//     onClose: function(selectedDate) {
//         $("#offdate").datepicker("option", "minDate", selectedDate);
//     }
// });

// $("#offdate").datepicker({
//     defaultDate: "+1w",
//     changeMonth: true,
//     changeYear: true,
//     dateFormat: "yy-mm-dd",
//     onClose: function(selectedDate) {
//         $("#launchdate").datepicker("option", "maxDate", selectedDate);
//     }
// });

function imagesPreview(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $("#evetit_poster_preview").attr("src", e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}