const API_URL = "http://localhost:8080";

async function login(){

    const username =
        document.getElementById("username").value;

    const password =
        document.getElementById("password").value;

    try{

        const response =
            await fetch(
                `${API_URL}/auth/login`,
                {
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify({
                        username,
                        password
                    })
                }
            );

        if(!response.ok){
            throw new Error("Credenciales inválidas");
        }

        const data =
            await response.json();

        localStorage.setItem(
            "token",
            data.token
        );

        alert("Login exitoso");

    }catch(error){

        alert(error.message);
    }
}

async function cargarProductos(){

    const token =
        localStorage.getItem("token");

    if(!token){

        alert(
            "Primero debes iniciar sesión"
        );

        return;
    }

    try{

        const response =
            await fetch(
                `${API_URL}/productos`,
                {
                    headers:{
                        "Authorization":
                            `Bearer ${token}`
                    }
                }
            );

        if(!response.ok){

            throw new Error(
                "Error al obtener productos"
            );
        }

        const productos =
            await response.json();

        mostrarProductos(productos);

    }catch(error){

        alert(error.message);
    }
}

function mostrarProductos(productos){

    const contenedor =
        document.getElementById(
            "productos"
        );

    contenedor.innerHTML = "";

    productos.forEach(producto => {

        contenedor.innerHTML += `
            <div class="producto-card">

                <h3>${producto.nombre}</h3>

                <p>
                    <strong>Precio:</strong>
                    $${producto.precio}
                </p>

                <p>
                    <strong>Stock:</strong>
                    ${producto.stock}
                </p>

                <p>
                    <strong>Categoría:</strong>
                    ${producto.categoria?.nombre ?? "Sin categoría"}
                </p>

            </div>
        `;
    });
}