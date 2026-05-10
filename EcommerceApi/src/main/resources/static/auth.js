
async function secureFetch(url, options = {}) {
    const defaultOptions = {
        credentials: 'include', // Crucial: para masama JSESSIONID cookie
        headers: {
            'Content-Type': 'application/json',
           ...options.headers
        },
       ...options
    };

    // Kuha CSRF token from meta tag kung meron
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

    if (csrfToken && csrfHeader) {
        defaultOptions.headers[csrfHeader] = csrfToken;
    }

    const response = await fetch(url, defaultOptions);

    // Task 7: Intercept Errors
    if (response.status === 401) {
        // 401 Unauthorized - not logged in
        alert('Session expired. Please login again.');
        window.location.href = '/login';
        throw new Error('Unauthorized');
    }

    if (response.status === 403) {
        // 403 Forbidden - logged in but wrong role
        alert('Access Denied. You do not have permission for this action.');
        throw new Error('Forbidden');
    }

    return response;
}

// Function to check if user is logged in
async function checkAuth() {
    try {
        const response = await secureFetch('/api/v1/auth/me');
        if (response.ok) {
            return await response.json();
        }
    } catch (error) {
        return null;
    }
    return null;
}