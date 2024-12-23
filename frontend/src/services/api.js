import axios from 'axios';



const api = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        "Content-Type": "application/json",
    },
});

/*
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('jwt');
    console.log(token);
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
},  (error) => Promise.reject(error));*/

export default api;

export const loginUser = async (credentials) => {
    const response = await api.post('/auth/login', credentials);
    return response.data; // `userId`
};

export const registerUser = async (userData) => {
    const response = await api.post('/auth/register', userData);
    return response.data;
};

export const getAllPosts = async () => {
    try {
        const response = await api.get('/posts/feed');
        return response.data; // Return the response data
    } catch (error) {
        console.error('Error fetching posts:', error);
        throw error; // Throw the error for the caller to handle
    }
};

export const addPost = async (content, userId) => {
    try {
        const response = await api.post(`/posts/${userId}`, content, { headers: { "Content-Type": "text/plain" }, });
        return response.data; // Return the response data
    } catch (error) {
        console.error('Error fetching posts:', error);
        throw error; // Throw the error for the caller to handle
    }
};

export const addComment = async (content, userId, postId) => {
    try {
        const response = await api.post(`/comments/${postId}?userId=${userId}`, content, { headers: { "Content-Type": "text/plain" }, });
        return response.data; // Return the response data
    } catch (error) {
        console.error('Error Commenting', error);
        throw error; // Throw the error for the caller to handle
    }
};

export const addReaction = (postId, userId) =>
    api.post(`/reactions/${postId}/like?userId=${userId}`);

export const getReactionsForPost = (postId) =>
    api.get(`/reactions/${postId}/likes`).then((res) => res.data);

// Search users
export const searchUsers = (query) =>
    api.get(`/search/users?query=${query}`).then((res) => res.data);

// Search posts
export const searchPosts = (query) =>
    api.get(`/search/posts?query=${query}`).then((res) => res.data);

export const getPostsbyUserId = async (userId) => {
    try {
        const response = await api.get(`/posts/${userId}`);
        return response.data; // Return the response data
    } catch (error) {
        console.error('Error getting user posts', error);
        throw error; // Throw the error for the caller to handle
    }
};

export const getUserProfile = async (username) => {
    try {
        const response = await api.get(`/auth/users/${username}`);
        return response.data; // Return the response data
    } catch (error) {
        console.error('Error getting user profile', error);
        throw error; // Throw the error for the caller to handle
    }
};

