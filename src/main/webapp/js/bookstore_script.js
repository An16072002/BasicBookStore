function activeAsLink(link) {
	window.location = link;
}

function onClickDeleteBook(title, bookId) {
	// Xác nhận xóa và tạo form để gửi dữ liệu về servlet
	var confirmation = confirm("Bạn có chắc chắn muốn xóa cuốn sách '" + title + "' không?");
	if (confirmation) {
		var form = document.createElement("form");
		form.setAttribute("method", "post");
		form.setAttribute("action", "${pageContext.request.contextPath}/deleteBook");

		var inputBookId = document.createElement("input");
		inputBookId.setAttribute("type", "hidden");
		inputBookId.setAttribute("name", "bookId");
		inputBookId.setAttribute("value", bookId);

		form.appendChild(inputBookId);
		document.body.appendChild(form);

		form.submit();
	}
}
var request;

function searchBooks(keyword) {
	if (window.XMLHttpRequest) {
		request = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		request = new ActiveXObject("Microsoft.XMLHTTP");
	}

	var url = request.getContextPath() + "/search?keyword=" + encodeURIComponent(keyword);

	try {
		request.onreadystatechange = function() {
			if (request.readyState == 4) {
				if (request.status == 200) {
					var val = request.responseText;
					document.getElementById('searchResultArea').innerHTML = val;
				} else {
					alert("Error: " + request.status + " " + request.statusText);
				}
			}
		};
		request.open("GET", url, true);
		request.send();
	} catch (e) {
		alert("Unable to connect to server");
	}
}

function plusValue(elementId, maxQuantity) {
		let quantity = parseInt(document.getElementById(elementId).value);
		if (quantity + 1 <= maxQuantity) {
			document.getElementById(elementId).value = quantity + 1;
		} else {
			alert('Giá trị không được vượt quá: ' + maxQuantity);
		}
	}

	function minusValue(elementId) {
		let quantity = parseInt(document.getElementById(elementId).value);
		if (quantity - 1 >= 1) {
			document.getElementById(elementId).value = quantity - 1;
		}
	}

function validateValue(element, maxQuantity) {
	if (element.value > maxQuantity) {
		alert("Giá trị không được vượt quá: " + maxQuantity);
	}
}

function checkQuantityAndSubmit(elementId, bookId, maxQuantity) {
	let quantity = parseInt(document.getElementById(elementId).value);
	if (quantity <= 0) {
		alert("Nhập số lượng!");
		return;
	} else if (quantity > maxQuantity) {
		alert("Giá trị không được vượt quá: " + maxQuantity);
	} else {
		document.getElementById("detailBookForm").submit();
	}
}

function onClickRemoveBook(bookTitle,bookId){
	let c = confirm('Bạn có chắc muốn xóa cuốn sách "' +bookTitle+'"khỏi giỏ hàng');
	if(c){
		document.getElementById("removedBookFromCart").value = bookId;
		document.getElementById("removedBookFromCartForm").submit();
	}
}

var request;
function updateQuantityOfCartItem(newQuantity,bookId){
	var url = 'addToCart?bookId='+bookId+'&quantityPurchased='+newQuantity;
	if(window.XMLHttpRequest){
		request = new XMLHttpRequest();
	}else if(window.ActiveXObject){
		request = new ActiveXObject("Microsoft.XMLHTTP");
	}
	try{
		request.onreadystatechange = getInfo;
		request.open("GET",url,true);
		request.send();
	}catch(e){
		alert("unable to connect to server");
	}
}

function getInfo(){
	if(request.readyState == 4){
		var val = request.responseText;
	}
}

function validateValueAndUpdateCart(element,maxQuantity,bookId,price){
	var newQuantity = element.value;
	if(newQuantity > maxQuantity){
		alert('Giá trị không được vượt quá : '+maxQuantity);
	}else if(newQuantity>0){
		updateQuantityOfCartItem(newQuantity,bookId);
		
		document.getElementById("subtotal"+bookId).innerText = toComma(newQuantity*price);
		
		let subtotalList = document.querySelectorAll('[id^="subtotal"]');
		let total = 0;
		for(let i = 0;i<subtotalList.length;i++){
			total+=parseInt(subtotalList[1].innerText.replace(/,/g,""));
		}
		document.getElementById("total").innerText = toComma(total);
	}
}

function toComma(n){
	return n.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",");
}

function minusValueAndUpdateCart(elementId){
	var quantity = parseInt(document.getElementById(elementId).value);
	if(quantity - 1 >=1){
		document.getElementById(elementId).value = quantity - 1;
		document.getElementById(elementId).onchange();
	}
}

function plusValueAndUpdateCart(elementId,maxQuantity){
	var quantity = parseInt(document.getElementById(elementId).value);
	if(quantity + 1 <=maxQuantity){
		document.getElementById(elementId).value = quantity +1;
		document.getElementById(elementId).onchange();
	}else{
		alert('Giá trị không được vượt quá : ' + maxQuantity);
	}
}

function loadImage(event){
	let output = document.getElementById('bookImage');
	output.src = URL.createObjectURL(event.target.files[0]);
	output.onload = function(){
		URL.revokeObjectURL(output.src)
	}
}

function onClickAdminOrderConfirm(orderId,confirmType,action){
	document.getElementById("orderIdOfAction").value = orderId;
	document.getElementById("confirmTypeOfAction").value = confirmType;
	document.getElementById("adminOrderForm").value = action.substring(1);
	document.getElementById("adminOrderForm").submit();
	
}
