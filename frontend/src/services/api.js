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

export const followUser = async (userId, targetUserId) => {
    try {
        const response = await api.post(`/connections/${userId}/follow`, targetUserId);
        return response.data;
    } catch (error) {
        console.error('Error following user:', error);
        throw error;
    }
};

export const unfollowUser = async (userId, targetUserId) => {
    try {
        const response = await api.delete(`/connections/${userId}/unfollow`, { data: targetUserId });
        return response.data;
    } catch (error) {
        console.error('Error unfollowing user:', error);
        throw error;
    }
};

export const getFollowers = async (userId) => {
    try {
        const response = await api.get(`/connections/${userId}/followers`);
        return response.data;
    } catch (error) {
        console.error('Error fetching followers:', error);
        throw error;
    }
}

export const ifFollows = async (userId, userId2) => {
    try {
        const response = await api.get(`/connections/${userId}/ifFollows/${userId2}`);
        return response.data;
    } catch (error) {
        console.error('Error if follows:', error);
        throw error;
    }
};

export const hasUserLikedPost = async (postId, userId) => {
    try {
        const response = await api.get(`/reactions/${userId}/user/${postId}`);
        return response.data; // This will be true or false
    } catch (error) {
        console.error("Error checking user reaction:", error);
        throw error;
    }
};

export const addLike = (postId, userId) =>
    api.post(`/reactions/${postId}/like/${userId}`);

export const unLike = (postId, userId) =>
    api.delete(`/reactions/${postId}/unlike/${userId}`);

export const getReactionsForPost = (postId) =>
    api.get(`/reactions/${postId}/likes`).then((res) => res.data);