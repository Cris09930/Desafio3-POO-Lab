<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${modoEdicion ? 'Editar' : 'Nuevo'} Vehículo</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script>
        function actualizarLabel() {
            const select = document.getElementById("idTipo");
            const option = select.options[select.selectedIndex];
            const label = document.getElementById("labelDato");
            if (option && option.text === "Moto") label.innerText = "🏍️ Cilindraje (CC):";
            else label.innerText = "⚖️ Capacidad (toneladas):";
        }
    </script>
</head>
<body class="form-vehiculo-page" onload="actualizarLabel()">
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card form-card">
                <div class="card-header-vehiculo text-center">${modoEdicion ? '✏️ Editar Vehículo' : '🚘 Registrar Vehículo'}</div>
                <div class="card-body p-4">
                    <form action="VehiculoServlet" method="post">
                        <input type="hidden" name="accion" value="${modoEdicion ? 'actualizar' : 'insertar'}">
                        <c:if test="${modoEdicion}"><input type="hidden" name="idVehiculo" value="${vehiculo.idVehiculo}"></c:if>
                        <div class="mb-3"><label class="form-label fw-semibold">🔧 Tipo</label><select name="idTipo" id="idTipo" class="form-select" onchange="actualizarLabel()" required><option value="">Seleccione...</option><c:forEach var="tipo" items="${tipos}"><option value="${tipo.idTipo}" ${vehiculo.idTipo == tipo.idTipo ? 'selected' : ''}>${tipo.nombreTipo}</option></c:forEach></select></div>
                        <div class="mb-3"><label class="form-label fw-semibold">🏷️ Marca</label><input type="text" name="marca" class="form-control" value="${vehiculo.marca}" required></div>
                        <div class="mb-3"><label class="form-label fw-semibold">📟 Modelo</label><input type="text" name="modelo" class="form-control" value="${vehiculo.modelo}" required></div>
                        <div class="mb-3"><label class="form-label fw-semibold">📅 Año</label><input type="number" name="anio" class="form-control" value="${vehiculo.anio}" required min="1900" max="2030"></div>
                        <div class="mb-3"><label id="labelDato" class="form-label fw-semibold">Dato específico</label><input type="number" step="any" name="datoEspecifico" class="form-control" value="${vehiculo.datoEspecifico}" required></div>
                        <div class="mb-4"><label class="form-label fw-semibold">🔧 Estado Mantenimiento</label><select name="estadoMantenimiento" class="form-select"><option value="Al día" ${vehiculo.estadoMantenimiento == 'Al día' ? 'selected' : ''}>✅ Al día</option><option value="Requiere revisión" ${vehiculo.estadoMantenimiento == 'Requiere revisión' ? 'selected' : ''}>⚠️ Requiere revisión</option></select></div>
                        <div class="d-flex gap-2"><button type="submit" class="btn btn-vehiculo-gradient w-50">💾 Guardar</button><a href="VehiculoServlet?accion=listar" class="btn btn-secondary w-50">❌ Cancelar</a></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
