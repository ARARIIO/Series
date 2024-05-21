<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<head>
	<meta charset="ISO-8859-1">
	<title>View Anime List</title>

	<link rel="stylesheet"
		  href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet"
		  href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
	<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script
			src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

	<style>
		a {
			color: white;
		}

		a:hover {
			color: white;
			text-decoration: none;
		}
	</style>

</head>
<body>

<div class="container">

	<h1 class="p-3"> Anime List</h1>

	<form:form>

		<table class="table table-bordered">
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Year</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>

			<c:forEach var="anime" items="${animeList}">
				<tr>
					<td>${anime.id}</td>
					<td>${anime.name}</td>
					<td>${anime.year}</td>
					<td>
						<button type="button" class="btn btn-success">
							<a href="/editAnime/${anime.id}">Edit</a>
						</button>
					</td>
					<td>
						<button type="button" class="btn btn-danger">
							<a href="/deleteAnime/${anime.id}">Delete</a>
						</button>
					</td>
				</tr>

			</c:forEach>

		</table>

	</form:form>

	<button type="button" class="btn btn-primary btn-block">
		<a href="${pageContext.request.contextPath}/addAnime">Add New Anime</a>
	</button>

	<form:form action="/createPayment" method="post" class="mt-4">
		<div class="form-group">
			<label for="amount" class="font-weight-bold">Pay by PayPal:</label>
			<input type="text" id="amount" name="amount" class="form-control" placeholder="Enter amount" required>
		</div>
		<button type="submit" class="btn btn-primary btn-lg btn-block">Send</button>
	</form:form>
	<style>
		form {
			background-color: #f8f9fa;
			padding: 20px;
			border-radius: 8px;
			box-shadow: 0 4px 8px rgba(0,0,0,0.1);
		}
		.form-group label {
			color: #495057;
		}
		.btn-primary {
			background-color: #007bff;
			border: none;
		}
		.btn-primary:hover {
			background-color: #0056b3;
		}
	</style>


</div>
<script src="https://cdn.jsdelivr.net/npm/web3/dist/web3.min.js"></script>
<script th:inline="javascript">
	window.onload = function () {

		var msg = "${message}";
		console.log(msg);
		if (msg === "Save Success") {
			toastr["success"]("Anime added successfully!!")
		} else if (msg === "Delete Success") {
			toastr["success"]("Anime deleted successfully!!")
		} else if (msg === "Delete Failure") {
			toastr["error"]("Some error occurred, couldn't delete user")
		} else if (msg === "Edit Success") {
			toastr["success"]("Anime updated successfully!!")
		}

		toastr.options = {
			"closeButton": true,
			"debug": false,
			"newestOnTop": false,
			"progressBar": true,
			"positionClass": "toast-top-right",
			"preventDuplicates": false,
			"showDuration": "300",
			"hideDuration": "1000",
			"timeOut": "5000",
			"extendedTimeOut": "1000",
			"showEasing": "swing",
			"hideEasing": "linear",
			"showMethod": "fadeIn",
			"hideMethod": "fadeOut"
		}
	}

</script>
</body>

</html>