<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${modoEdicion ? 'Editar' : 'Nuevo'} Conductor</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="form-conductor-page">
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card form-card">
                <div class="card-header-conductor text-center">
                    ${modoEdicion ? '✏️ Editar Conductor' : '📝 Registrar Conductor'}
                </div>
                <div class="card-body p-4">
                    <form action="ConductorServlet" method="post">
                        <input type="hidden" name="accion" value="${modoEdicion ? 'actualizar' : 'insertar'}">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">📄 DUI (00000000-0)</label>
                            <input type="text" name="dui" class="form-control" placeholder="Ej: 12345678-9" value="${conductor.dui}" ${modoEdicion ? 'readonly' : ''} required pattern="\d{8}-\d">
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">👤 Nombre Completo</label>
                            <input type="text" name="nombreCompleto" class="form-control" placeholder="Nombres y apellidos" value="${conductor.nombreCompleto}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">🎂 Edad</label>
                            <input type="number" name="edad" class="form-control" placeholder="Mínimo 18 años" value="${conductor.edad}" required min="18">
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">⚥ Sexo</label>
                            <select name="sexo" class="form-select">
                                <option value="M" ${conductor.sexo == 'M' ? 'selected' : ''}>Masculino</option>
                                <option value="F" ${conductor.sexo == 'F' ? 'selected' : ''}>Femenino</option>
                            </select>
                        </div>
                        <div class="mb-4">
                            <label class="form-label fw-semibold">✅ Licencia Vigente</label>
                            <select name="licenciaVigente" class="form-select">
                                <option value="true" ${conductor.licenciaVigente ? 'selected' : ''}>Sí</option>
                                <option value="false" ${not conductor.licenciaVigente ? 'selected' : ''}>No</option>
                            </select>
                        </div>
                        <div class="d-flex justify-content-between gap-2">
                            <button type="submit" class="btn btn-primary-gradient w-50">💾 Guardar</button>
                            <a href="ConductorServlet?accion=listar" class="btn btn-secondary w-50">❌ Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
