import styles from './Comment.module.css';
import React from 'react';
import { deleteComment } from '../api/comments/delete';

const Comment = ({ info, validDelete, onDelete, studentId, cardsetId }) => {
	const clickHandler = async () => {
		const deleteCommentData = await deleteComment(
			studentId,
			cardsetId,
			info.cardId,
			info.commentId
		);
		onDelete();
	};

	return (
		<div className={styles.container}>
			<div className={styles.title}>{info.title}</div>
			<div>{info.content}</div>
			{validDelete && (
				<div>
					<button onClick={clickHandler}>delete comment</button>
				</div>
			)}
		</div>
	);
};

export default Comment;
