<!DOCTYPE html>
<html>
<head>
    <title>
        MALSAT
    </title>
</head>
<body>
<h1>MALSAT</h1><hr>
<h4>Скот со всего Кыргызстана</h4>
<form action="/" method="get">
    Поиск по названию объявления: <input type="text" name="title"><br>
    <input type="submit" value="Поиск"/>
</form>
<#list products as product>
    <div>
        <p>
            <b>${product.title}</b>
            ${product.price} сом. |
            <a href="/product/${product.id}">Подробнее...</a>
        </p>
    </div>
    <#else>
    <h3>Товаров нет</h3>
</#list>
<hr>
<h3>Создать новый товар</h3>
<form action="/product/create" method="post">
    Название обьявления: <input type="text" name="title" required/><br><br>
    Описание обьявления: <input type="text" name="description" required/><br><br>
    Цена обьявления: <input type="number" name="price" required/><br><br>
    Город обьявления: <input type="text" name="city" required/><br><br>
    Имя обьявления: <input type="text" name="author" required/><br><br>
    Первая фотография: <input type="file" name="file1" required/><br><br>
    Вторая фотография: <input type="file" name="file2" required/><br><br>
    Третья фотография: <input type="file" name="file3" required/><br><br>
    <input type="submit" value="Добавить товар"/>
</form>
</body>
</html>