<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Historial de Viajes</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="page-header page-header-viajes">
    <div class="container">
        <h2>📋 Historial de Viajes</h2>
        <p>Registro de rutas realizadas</p>
    </div>
</div>
<div class="container mt-4">
    <div class="d-flex justify-content-between mb-3"><div><strong>🚀 Viajes totales:</strong> ${viajes.size()}</div><div><a href="ViajeServlet?accion=nuevo" class="btn btn-info rounded-pill-custom px-4">+ Nuevo Viaje</a><a href="index.jsp" class="btn btn-secondary rounded-pill-custom px-4">Inicio</a></div></div>
    <c:if test="${not empty sessionScope.mensaje}"><div class="alert alert-success">${sessionScope.mensaje}</div><c:remove var="mensaje" scope="session"/></c:if>
    <div class="table-responsive"><table class="table table-bordered table-hover bg-white rounded overflow-hidden"><thead class="table-dark"><tr><th>ID</th><th>Conductor</th><th>Vehículo</th><th>Distancia</th><th>Costo ($)</th><th>Fecha</th></tr></thead>
        <tbody><c:forEach var="v" items="${viajes}"><tr><td>${v.idViaje}</td><td>${v.nombreConductor}</td><td>${v.descripcionVehiculo}</td><td>${v.distanciaKm} km</td><td class="fw-bold text-success">$${v.costo}</td><td>${v.fechaViaje}</td></tr></c:forEach><c:if test="${empty viajes}"><tr><td colspan="6" class="text-center text-muted">No hay viajes registrados</td></tr></c:if></tbody></table></div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
