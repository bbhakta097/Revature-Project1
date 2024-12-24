import React from 'react';

const CommentList = ({ comments, onDeleteComment }) => {
    if (!comments.length) {
        return <p>No comments yet.</p>;
    }

    return (
        <ul>
            {comments.map((comment) => (
                <li key={comment.id}>
                    <p>{comment.content} <button
                        style={{
                            backgroundColor: 'red',
                            color: 'white',
                            border: 'none',
                            padding: '1px 7px',
                            cursor: 'pointer',
                            alignItems: 'flex-end'
                        }}
                        onClick={() => onDeleteComment(comment.id)}> X </button></p>
                    <small>By: {comment.commenter?.username || "Unknown User"}</small>
                    <small>On: {new Date(comment.createdAt).toLocaleString()}</small>

                </li>
            ))}
        </ul>
    );
};

export default CommentList;
