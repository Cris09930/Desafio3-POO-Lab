<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reporte General</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="reporte-page">
<div class="container py-4">
    <div class="report-header text-center"><h1>📈 Reporte Final - Transportes Don Bosco</h1><p>Resumen ejecutivo de operaciones</p></div>
    <div class="row"><div class="col-md-4"><div class="card-report card p-3"><h5>👨‍✈️ Conductores</h5><h2 class="text-primary">${conductores.size()}</h2><small>Registrados en el sistema</small></div></div>
        <div class="col-md-4"><div class="card-report card p-3"><h5>🚗 Vehículos</h5><h2 class="text-success">${vehiculos.size()}</h2><small>En flota activa</small></div></div>
        <div class="col-md-4"><div class="card-report card p-3"><h5>🗺️ Viajes</h5><h2 class="text-info">${viajes.size()}</h2><small>Realizados</small></div></div></div>

    <div class="card mt-4"><div class="card-header bg-dark text-white">Conductores</div><div class="card-body"><c:if test="${empty conductores}"><p>No hay conductores.</p></c:if><ul><c:forEach var="c" items="${conductores}"><li>${c.nombreCompleto} (${c.dui}) - ${c.edad} años - <span class="badge ${c.licenciaVigente ? 'bg-success' : 'bg-danger'}">${c.licenciaTexto}</span></li></c:forEach></ul></div></div>
    <div class="card mt-3"><div class="card-header bg-dark text-white">Vehículos</div><div class="card-body"><ul><c:forEach var="v" items="${vehiculos}"><li>${v.marca} ${v.modelo} (${v.nombreTipo}) - ${v.anio} - ${v.estadoMantenimiento}</li></c:forEach></ul></div></div>
    <div class="card mt-3"><div class="card-header bg-dark text-white">Viajes</div><div class="card-body"><c:if test="${empty viajes}"><p>No hay viajes registrados.</p></c:if><table class="table table-sm"><thead><tr><th>Conductor</th><th>Vehículo</th><th>Distancia</th><th>Costo</th><th>Fecha</th></thead><tbody><c:forEach var="v" items="${viajes}"><tr><td>${v.nombreConductor}</td><td>${v.descripcionVehiculo}</td><td>${v.distanciaKm} km</td><td class="fw-bold">$${v.costo}</td><td>${v.fechaViaje}</td></tr></c:forEach></tbody></table></div></div>
    <div class="highlight mt-4"><h5>🏆 Viaje más costoso</h5><c:choose><c:when test="${not empty viajeMasCaro}"><strong>${viajeMasCaro.nombreConductor}</strong> realizó un viaje de <strong>${viajeMasCaro.distanciaKm} km</strong> en <strong>${viajeMasCaro.descripcionVehiculo}</strong> con costo de <strong class="text-success">$${viajeMasCaro.costo}</strong> (${viajeMasCaro.fechaViaje})</c:when><c:otherwise>No hay viajes registrados.</c:otherwise></c:choose></div>
    <div class="text-center mt-4"><a href="index.jsp" class="btn btn-primary rounded-pill-custom px-5">🏠 Volver al Inicio</a></div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
