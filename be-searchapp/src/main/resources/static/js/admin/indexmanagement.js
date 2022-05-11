$(document).ready(function() {

	// load first when coming page
	ajaxGet(1);

	function ajaxGet() {
		page = 1;
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/api/elastic/getindex",
			success: function(result) {
				var strbody = "";
				$.each(result.object, function(i, index) {
					strbody += '<tr>' +
						'<td>' + index + '</td>'

						+ '<td><button class="btn btn-primary btnCapNhat" >Cập nhật</button>'
						+ '<button class="btn btn-danger btnXoa" >Xóa</button></td>';
					+'</tr>'


				});
				$('.taiKhoanTable tbody').html(strbody);
				/*if (result.totalPages > 1) {
					for (var numberPage = 1; numberPage <= result.totalPages; numberPage++) {
						var li = '<li class="page-item "><a class="pageNumber">' + numberPage + '</a></li>';
						$('.pagination').append(li);
					};

					// active page pagination
					$(".pageNumber").each(function(index) {
						if ($(this).text() == page) {
							$(this).parent().removeClass().addClass("page-item active");
						}
					});
				};*/
			},
			error: function(e) {
				alert("Error: ", e);
				console.log("Error", e);
			}
		});
	};

	// event khi click vào dropdown chọn danh mục thêm sản phẩm
	$('#vaiTro').mouseup(function() {
		var open = $(this).data("isopen");
		if (open) {
			resetData();
		}
		$(this).data("isopen", !open);
	});

	// click thêm tài khoản
	$(document).on('click', '.btnThemTaiKhoan', function(event) {
		event.preventDefault();
		$("#taiKhoanModal").modal();
	});


	// click thêm tài khoản
	$(document).on('click', '.btnUpdate', function(event) {
		event.preventDefault();
		$("#taiKhoanModal").modal();
	});

	// xác nhận thêm tài khoản
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
				ajaxGet(1);
				alert("Thêm mới bài index thành công");

			},

			error: function(e) {
				alert("error");
				$('#taiKhoanModal').modal('hide');
				console.log("ERROR: ", e);
			}


		});

	}

	// delete request
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

	// event khi ẩn modal form
	$('#taiKhoanModal').on('hidden.bs.modal', function(e) {
		e.preventDefault();
		$('.taiKhoanForm input').next().remove();
	});

	// reset table after post, put, filter
	function resetData() {
		var page = $('li.active').children().text();
		$('.taiKhoanTable tbody tr').remove();
		$('.pagination li').remove();
		ajaxGet(page);
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