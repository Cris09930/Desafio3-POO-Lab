<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrar Viaje</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="form-viaje-page">
<div class="container py-5">
    <div class="row justify-content-center"><div class="col-md-8"><div class="card shadow-lg border-0 rounded-4"><div class="card-header-viaje text-center fw-bold fs-4">🗺️ Registro de Nuevo Viaje</div><div class="card-body p-4">
        <c:if test="${empty conductores or empty vehiculos}"><div class="alert alert-warning">⚠️ No hay conductores con licencia vigente o vehículos en estado "Al día". Debe registrar al menos uno válido.</div></c:if>
        <form action="ViajeServlet" method="post">
            <div class="mb-3"><label class="form-label fw-semibold">👨‍✈️ Conductor</label><select name="duiConductor" class="form-select" required><c:forEach var="c" items="${conductores}"><option value="${c.dui}">${c.nombreCompleto} (${c.dui})</option></c:forEach></select></div>
            <div class="mb-3"><label class="form-label fw-semibold">🚐 Vehículo</label><select name="idVehiculo" class="form-select" required><c:forEach var="v" items="${vehiculos}"><option value="${v.idVehiculo}">${v.marca} ${v.modelo} (${v.nombreTipo})</option></c:forEach></select></div>
            <div class="mb-4"><label class="form-label fw-semibold">📏 Distancia (km)</label><input type="number" step="any" name="distanciaKm" class="form-control" required min="0.1" placeholder="Ej: 120.5"></div>
            <div class="d-flex gap-2"><button type="submit" class="btn btn-viaje-gradient w-50 fw-bold">💰 Calcular y Registrar</button><a href="index.jsp" class="btn btn-secondary w-50">← Cancelar</a></div>
        </form>
    </div></div></div></div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
