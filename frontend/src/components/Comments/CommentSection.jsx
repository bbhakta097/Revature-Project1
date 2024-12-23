import React, { useState, useEffect } from "react";
import api from "../../services/api";
import CommentList from "./CommentList";
import CommentForm from "./CommentForm";

const CommentSection = ({ postId, userId }) => {
    const [comments, setComments] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchComments = async () => {
            try {
                const response = await api.get(`/comments/${postId}`);
                setComments(response.data);
            } catch (error) {
                console.error("Error fetching comments:", error);
                setError("Failed to load comments.");
            }
        };

        fetchComments();
    }, [postId]);

    const handleCommentAdded = (newComment) => {
        setComments((prevComments) => [newComment, ...prevComments]);
    };

    const handleDeleteComment = async (commentId) => {
        try {
            await api.delete(`/comments/${commentId}`);
            setComments((prevComments) =>
                prevComments.filter((c) => c.id !== commentId)
            );
        } catch (error) {
            console.error("Failed to delete comment:", error);
        }
    };

    return (
        <div>
            <CommentList comments={comments} onDeleteComment={handleDeleteComment} />
            <br></br>
            <CommentForm
                postId={postId}
                userId={userId}
                onCommentAdded={handleCommentAdded}
            />
        </div>
    );
};

export default CommentSection;
