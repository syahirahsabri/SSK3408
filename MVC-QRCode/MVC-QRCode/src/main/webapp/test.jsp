<html>
<head>
<title>Page Title</title>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	$(function() {

		$('#fiad').change(function(event) {
			var tmppath = URL.createObjectURL(event.target.files[0]);
			$("img").fadeIn("fast").attr('src', tmppath);

		});
	});
</script>

</head>
<body>

	<input type="file" id="fiad">
	<br>
	<br>

	<img src="" width="200" style="display: none;" />

</body>
</html>