<!DOCTYPE html>
<html>
<head>
    <title>
        MALSAT
    </title>
</head>
<body>
<h1>MALSAT</h1><hr>
<h4>Подробная информация о скоте</h4>
<b>Название товара: </b> ${product.title}
<b>Описание: </b> ${product.description}
<b>Цена: </b> ${product.price}
<b>Город: </b> ${product.city}
<b>Автор: </b> ${product.author}
<hr>
<form action="/product/delete/${product.id}" method="post">
    <input type="submit" value="Удалить товар"/>
</form>
</body>
</html>