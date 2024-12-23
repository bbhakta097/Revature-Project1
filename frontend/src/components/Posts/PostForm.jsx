import React, { useState } from "react";

const PostForm = ({ onAddPost }) => {
  const [content, setContent] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (content.trim() === "") {
      alert("Post content cannot be empty.");
      return;
    }
    await onAddPost(content);
    setContent("");
  };

  return (
    <form onSubmit={handleSubmit} className="post-form">
      <textarea
        value={content}
        onChange={(e) => setContent(e.target.value)}
        placeholder="What's on your mind?"
        className="post-textarea"
      />
      <button type="submit" className="post-submit-button">
        Post
      </button>
    </form>
  );
};

export default PostForm;
