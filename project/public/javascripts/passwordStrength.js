/**
 * Created by EHJUNG on 2017-04-10.
 */
$(document).ready(function() {
    $('#password').strengthMeter('progressBar', {
        container: $('#password-strength-bar'),
        hierarchy: {
            '0': 'progress-bar-danger',
            '10': 'progress-bar-warning',
            '15': 'progress-bar-success'
        }
    });
});