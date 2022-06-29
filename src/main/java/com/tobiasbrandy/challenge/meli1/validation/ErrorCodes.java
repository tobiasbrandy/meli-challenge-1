package com.tobiasbrandy.challenge.meli1.validation;

public final class ErrorCodes {
    private ErrorCodes() {
        // static
    }

    private static final int SATELLITE_ERROR_CODE = 700;
    public static ApplicationException satelliteNotFound(final String satellite) {
        return new ApplicationException(404, SATELLITE_ERROR_CODE + 1, "Satellite %s not found", satellite);
    }
    public static ApplicationException satelliteCreationFailed(final String message) {
        return new ApplicationException(SATELLITE_ERROR_CODE + 2, "Error during satellite creation: " + message);
    }
    public static ApplicationException satelliteComUpdateFailed(final String message) {
        return new ApplicationException(SATELLITE_ERROR_CODE + 3, "Error during satellite update: " + message);
    }

    private static final int SATELLITE_COM_ERROR_CODE = 800;
    public static ApplicationException satelliteComNotFound(final String satellite) {
        return new ApplicationException(404, SATELLITE_COM_ERROR_CODE + 1, "Satellite %s com not found", satellite);
    }
    public static ApplicationException satellitesForTriangulationNotFound() {
        return new ApplicationException(404, SATELLITE_COM_ERROR_CODE + 2, "Some satellites chosen for triangulation were not found");
    }
    public static ApplicationException satellitesComForTriangulationNotFound() {
        return new ApplicationException(404, SATELLITE_COM_ERROR_CODE + 3, "Some satellites chosen for triangulation did not have an associated communication");
    }

}
