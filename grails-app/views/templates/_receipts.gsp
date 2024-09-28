<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <style>
    body {
        font-family: Arial, sans-serif;
        margin: 20px;
    }
    div {
        padding: 15px;
    }
    h1 {
        text-align: center;
        color: #007BFF;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }
    table, th, td {
        border: 1px solid black;
    }
    th, td {
        padding: 10px;
        text-align: left;
    }
    .footer {
        text-align: center;
        margin-top: 30px;
        font-size: 12px;
    }
    </style>
</head>
<body>
<h1>Recibo de venta</h1>
<div>
    <p><strong>Identificación del cliente:</strong> ${receipt.client.id}</p>
    <p><strong>Nombre del cliente:</strong> ${receipt.client.name}</p>
    <p><strong>Email:</strong> ${receipt.client.email}</p>
    <p><strong>Dirección:</strong> ${receipt.client.address}</p>
    <p><strong>Fecha de recibo:</strong> ${receipt.dateOrder}</p>
</div>

<h2>Productos</h2>
<table>
    <thead>
    <tr>
        <th>Nombre del producto</th>
        <th>Cantidad</th>
        <th>Precio</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${receipt.order.products}" var="product">
        <tr>
            <td>${product.name}</td>
            <td>${product.quantity}</td>
            <td>${product.price}</td>
        </tr>
    </g:each>
    </tbody>
</table>

<p><strong>Total:</strong> ${receipt.total}</p>

<div class="footer">
    <p>Gracias por tu pedido!</p>
</div>
</body>
</html>