$(document).ready(function() {

	// load first when coming page
	ajaxGet(true);
	var finalsearchParam = [];
	function ajaxGet(booloadIndex) {
		page = 1;
		if (booloadIndex) {
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: "http://localhost:8080/api/elastic/getindex",
				success: function(result) {
					var strbody = "";
					$('#listIndex').html(strbody);
					$('#listIndex').append('<option value="-1">-Chọn index-</option>');
					$.each(result.object, function(i, index) {
						var op = '<option value="' + index + '">' + index + '</option>'
						$('#listIndex').append(op);
					});

				},
				error: function(e) {
					alert("Error: ", e);
					console.log("Error", e);
				}
			});
		}
		var Indexval = $('#listIndex').val();
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/api/elastic/getlabel",
			data: JSON.stringify({ "index": Indexval }),
			success: function(result) {
				$('.searchfield').html("");
				if (result.object != null) {
					$.each(result.object, function(i, index) {

						var li = '<label class="checkbox-inline">'
							+ '<input class="checksearch" type="checkbox" value="" id="searchparam.' + index + '">' + index
							+ '</label>'

						$('.searchfield').append(li);

					});
				}

			},
			error: function(e) {
				alert("Error: ", e);
				console.log("Error", e);
			}
		});
	};

	$(document).on('change', '#listIndex', function(event) {

		ajaxGet(false);
	});

	$(document).on('change', '.checksearch', function(event) {

		var searchId = $(this).attr('id');
		var fields = searchId.split('.');
		var searchParam = fields[1];

		if (this.checked) {
			const searchParamtmp = { key: searchParam };
			finalsearchParam.push(searchParamtmp);
		}
		else {
			finalsearchParam = finalsearchParam.filter(param => param.key != searchParam);
		}
		console.log(finalsearchParam)
	});

	$(document).on('click', '.searchsubmit', function(event) {

		var keyword = $('#searchKeyword').val();
		var filterParams = finalsearchParam;
		var datainp = { index: "phap_test11112", keyword: keyword, filterParams: filterParams };
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/api/elastic/search",
			data: JSON.stringify(datainp),
			success: function(result) {
				$('#searchResult').html('');
				let data = result.object[0];
				let thead = document.createElement("THEAD");
				let rowh = document.createElement("TR");
				$.each(Object.keys(data), function(i, key) {
					let th = document.createElement("th");
					let text = document.createTextNode(key);
					th.append(text);
					rowh.append(th);
				});
				thead.append(rowh);
				$('#searchResult').append(thead);

				console.log(result);
				result.object.forEach(({ uuid, ...rest }) => {
					const row = document.createElement("TR");
					Object.entries(rest).forEach(([k, v]) => {
						const cell = document.createElement("TD");
						if (k === "status") {
							cell.innerHTML = `
			                <button value="${uuid}" class="approve-btn">Approve</button>
			                <button value="${uuid}" class="deny-btn">Deny</button>`;
						} else {
							cell.innerHTML = v;
						}
						row.append(cell);
					});
					$('#searchResult').append(row);
				});

			},
			error: function(e) {
				alert("Error: ", e);
				console.log("Error", e);
			}
		});

	});

	$(document).on('click', '.btnThemTaiKhoan', function(event) {
		event.preventDefault();
		$("#taiKhoanModal").modal();
	});


	$(document).on('click', '.btnUpdate', function(event) {
		event.preventDefault();
		$("#taiKhoanModal").modal();
	});


	$(document).on('click', '#btnSubmit', function(event) {
		event.preventDefault();
		ajaxPostTaiKhoan();
		resetData();
	});

	function ajaxPostTaiKhoan() {

		var formData = new FormData();
		var name = $('#indexname').val();

		file_json = $('#fileJson')[0].files[0];
		formData.append("file", file_json);
		formData.append("index", name);
		$.ajax({
			data: formData,
			type: 'POST',
			url: "http://localhost:8080/api/elastic/import",
			enctype: 'multipart/form-data',
			contentType: false,
			cache: false,
			processData: false,
			success: function(data) {

				$('#taiKhoanModal').modal('hide');
				ajaxGet(false);
				alert("Thêm mới bài index thành công");

			},

			error: function(e) {
				alert("error");
				$('#taiKhoanModal').modal('hide');
				console.log("ERROR: ", e);
			}


		});

	}


	$(document).on("click", ".btnXoa", function() {

		var taiKhoanId = $(this).parent().prev().children().val();
		var confirmation = confirm("Bạn chắc chắn xóa tài khoản này ?");
		if (confirmation) {
			$.ajax({
				type: "DELETE",
				url: "http://localhost:8080/api/admin/tai-khoan/delete/" + taiKhoanId,
				success: function(resultMsg) {
					alert("Xóa thành công")
					resetData();
				},
				error: function(e) {
					console.log("ERROR: ", e);
				}
			});
		}
	});


	$('#taiKhoanModal').on('hidden.bs.modal', function(e) {
		e.preventDefault();
		$('.taiKhoanForm input').next().remove();
	});


	function resetData() {
		var page = $('li.active').children().text();
		$('.taiKhoanTable tbody tr').remove();
		$('.pagination li').remove();
		ajaxGet(false);
	};

	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [o[this.name]];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);

	// remove element by class name
	function removeElementsByClass(className) {
		var elements = document.getElementsByClassName(className);
		while (elements.length > 0) {
			elements[0].parentNode.removeChild(elements[0]);
		}
	}
});