<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vehículos | Transportes Don Bosco</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="page-header page-header-vehiculos">
    <div class="container">
        <h2>🚗 Gestión de Vehículos</h2>
        <p>Flota de Transportes Don Bosco</p>
    </div>
</div>
<div class="container">
    <div class="d-flex justify-content-between mb-3">
        <div><strong>🚚 Total vehículos:</strong> ${vehiculos.size()}</div>
        <div>
            <a href="VehiculoServlet?accion=nuevo" class="btn btn-success rounded-pill-custom px-4">+ Nuevo Vehículo</a>
            <a href="index.jsp" class="btn btn-secondary rounded-pill-custom px-4">← Inicio</a>
        </div>
    </div>
    <c:if test="${not empty sessionScope.mensaje}">
        <div class="alert alert-success alert-dismissible fade show">${sessionScope.mensaje}<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>
        <c:remove var="mensaje" scope="session"/>
    </c:if>
    <div class="table-responsive">
        <table class="table table-custom table-hover">
            <thead class="table-dark">
            <tr><th>ID</th><th>Tipo</th><th>Marca/Modelo</th><th>Año</th><th>Dato Específico</th><th>Estado</th><th>Acciones</th></tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${vehiculos}">
                <tr>
                    <td>${v.idVehiculo}</td>
                    <td><span class="badge bg-info">${v.nombreTipo}</span></td>
                    <td><strong>${v.marca}</strong> ${v.modelo}</td>
                    <td>${v.anio}</td>
                    <td>${v.datoEspecificoFormateado}</td>
                    <td><span class="badge ${v.estadoMantenimiento == 'Al día' ? 'bg-success' : 'bg-warning'}">${v.estadoMantenimiento}</span></td>
                    <td><a href="VehiculoServlet?accion=editar&id=${v.idVehiculo}" class="btn btn-warning btn-sm">Editar</a> <a href="VehiculoServlet?accion=eliminar&id=${v.idVehiculo}" class="btn btn-danger btn-sm" onclick="return confirm('¿Eliminar?')">Eliminar</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty vehiculos}"><tr><td colspan="7" class="text-center">No hay vehículos</td></tr></c:if>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
