<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Conductores | Transportes Don Bosco</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="page-header page-header-conductores">
    <div class="container">
        <h2><i class="bi bi-people-fill"></i> 👨‍✈️ Gestión de Conductores</h2>
        <p class="mb-0">Listado completo de conductores registrados en el sistema</p>
    </div>
</div>

<div class="container mt-4">
    <div class="card-stats d-flex justify-content-between align-items-center">
        <div>
            <strong>📊 Total conductores:</strong> ${conductores.size()}
        </div>
        <div>
            <a href="ConductorServlet?accion=nuevo" class="btn btn-primary btn-sm rounded-pill-custom px-4">+ Nuevo Conductor</a>
            <a href="index.jsp" class="btn btn-secondary btn-sm rounded-pill-custom px-4">← Volver al Menú</a>
        </div>
    </div>

    <c:if test="${not empty sessionScope.mensaje}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
            <i class="bi bi-check-circle-fill"></i> ${sessionScope.mensaje}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="mensaje" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.mensajeError}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
            <i class="bi bi-exclamation-triangle-fill"></i> ${sessionScope.mensajeError}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="mensajeError" scope="session"/>
    </c:if>

    <div class="table-responsive">
        <table class="table table-custom table-hover align-middle">
            <thead>
            <tr>
                <th>DUI</th><th>Nombre Completo</th><th>Edad</th><th>Sexo</th><th>Licencia Vigente</th><th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="c" items="${conductores}">
                <tr>
                    <td class="fw-bold">${c.dui}</td>
                    <td>${c.nombreCompleto}</td>
                    <td>${c.edad} años</td>
                    <td><span class="badge ${c.sexo == 'M' ? 'bg-info' : 'bg-warning'}">${c.sexo == 'M' ? 'Masculino' : 'Femenino'}</span></td>
                    <td><span class="badge ${c.licenciaVigente ? 'bg-success' : 'bg-danger'}">${c.licenciaTexto}</span></td>
                    <td>
                        <a href="ConductorServlet?accion=editar&dui=${c.dui}" class="btn btn-warning btn-sm">✏️ Editar</a>
                        <a href="ConductorServlet?accion=eliminar&dui=${c.dui}" class="btn btn-danger btn-sm" onclick="return confirm('¿Eliminar conductor?')">🗑️ Eliminar</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty conductores}">
                <tr><td colspan="6" class="text-center text-muted py-4">No hay conductores registrados</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
