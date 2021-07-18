 /** Funcion para manejar la ventana modal para modificar el precio */
	   
 		window.addEventListener('click', clickFueraPrecio);
	function modificar(id, precio){
		
		document.getElementById('modificar-precio').value = id;
		document.getElementById('precio-valor').value = precio;
		document.getElementById('VentanaPrecio').style.display = 'block';
	
}
	 function abreVentanaPrecio(){
	   document.getElementById('VentanaPrecio').style.display = 'block';
   }
    function cerrarVentanaPrecio(){
		document.getElementById('VentanaPrecio').style.display = 'none';
	}
	

   function clickFueraPrecio(event){
		if(event.target == document.getElementById('VentanaPrecio')){
			document.getElementById('VentanaPrecio').style.display = 'none';
		}
	}
	

/**
 * 
 */
 function preReserva(habitacionID){
 	let cantidad = 1;
 	
 	axios.post(`/preReserva/anade`, null, {
 		params: {
 			habitacionID,
 			cantidad
 			}
 		}).then(function(){
 			  alert("Habitacion prereservada!");
 		}).catch(function(){
 			  alert("Ha habido un error. Vuelve a anadir");
 			  });
 }
 
 function Buscar(){ 
		 console.log("estas en Buscar()");
		let request = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
		let container = document.getElementById('card-container');
		let buscar = document.getElementById('buscarTitulo').value;
		
		let url = '/recursos/'+`${buscar}`;
		
		request.open("GET", url , true);
		request.onreadystatechange = function() {
			
			console.log('Hecho:', request.status);
				console.log('Hecho:', url);
			if(this.readyState === 4 && this.status === 200){	
				
			 	let data = JSON.parse(request.responseText);
				if(data != null){
					let output='';
					data.forEach(item => {
						
		                    output +=`
							<div class="card mt-5 ml-5 mb-5">
		                    <img  class="cImg" src="img/${item.img_ruta}" alt="foto"></img>
		                    <p>${item.nombre}</p>
		                    <p class="price" >Precio: ${item.precio} EUR</p>
          				<p class="small">${item.descripcion}</p>
          				<button >Reservar</button>
          				</div>
		                    `;
						 
					})
					document.getElementById('card-container').innerHTML=output;
					}else{
						 console.log("Array is empty!")
					container.innerHTML =`<h2>De momento no hay titulos con este nombre</h2>`
					}
					
					
				} else {
					console.error("La conexion ha fallado");
				}	
		 }
		 
		request.send(null);
		
 };
 