import React, { use, useState } from 'react';
import api, { addComment } from '../../services/api';
import { useNavigate } from 'react-router-dom';

const CommentForm = ({ postId, userId, onCommentAdded }) => {
    const [content, setContent] = useState('');
    const nav = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (content.trim() === "") {
            alert("Comment cannot be empty.");
            return;
        }

        try {
            const response = addComment(content, userId, postId);
            onCommentAdded(response); // Notify parent to refresh comments
            setContent(""); // Clear the textarea
            nav(0);
        } catch (error) {
            console.error("Failed to add comment:", error);
        }
    };

    return (
        <form onSubmit={handleSubmit} className='comment-form'>
            <input
                type="text"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                placeholder="Write a comment..."
            />
            <button type="submit">Comment</button>
        </form>
    );
};

export default CommentForm;
