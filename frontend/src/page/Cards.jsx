import React from 'react';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { readAll } from '../api/cards/readAll';
import Card from '../component/Card';
import styles from './Cards.module.css';
import { create } from '../api/cards/create';
import { update } from '../api/cards/update';
import { deleteCard } from '../api/cards/delete';
import { exportCards } from '../api/cards/export';
import Comment from '../component/Comment';
import { readAllComment } from '../api/comments/readAll';
import { createComment } from '../api/comments/create';
import { readAll as readAllCardset } from '../api/cardsets/readAll';

const Cards = () => {
	let data = null;
	const [cards, setCards] = useState([]);
	const [idx, setIdx] = useState(0);
	const [upTitle, setUpTitle] = useState('');
	const [upContent, setUpContent] = useState('');
	const [downTitle, setDownTitle] = useState('');
	const [downContent, setDownContent] = useState('');
	const [changeIdx, setChangeIdx] = useState('1');
	const [comments, setComments] = useState([]);
	const [commentTitle, setCommentTitle] = useState('');
	const [commentContent, setCommentContent] = useState('');
	const [validDelete, setValidDelete] = useState(false);

	const { studentId, cardsetId } = useParams();

	const readAllHandler = async () => {
		data = await readAll({ studentId, cardsetId });
		setCards(data);
		setComments([]);
	};

	useEffect(() => {
		readAllHandler();
		deleteCommentHandler();
	}, []);

	const prevHandler = () => {
		if (idx - 1 >= 0) {
			setIdx((prev) => prev - 1);
		}
		setComments([]);
	};

	const nextHandler = () => {
		if (idx + 1 < cards.length) {
			setIdx((prev) => prev + 1);
		}
		setComments([]);
	};

	const createHandler = async () => {
		const params = {
			studentId,
			cardsetId,
			body: {
				upTitle,
				upContent,
				downTitle,
				downContent,
				cardsetId,
			},
		};
		const creatData = await create(params);
		readAllHandler();
	};
	const updateHandler = async () => {
		const params = {
			studentId,
			cardsetId,
			cardId: cards[changeIdx - 1].cardId,
			body: {
				upTitle,
				upContent,
				downTitle,
				downContent,
				cardsetId,
			},
		};
		const updateData = await update(params);
		readAllHandler();
	};
	const deleteHandler = async () => {
		const deleteData = await deleteCard(
			studentId,
			cardsetId,
			cards[changeIdx - 1].cardId
		);
		setIdx(0);
		readAllHandler();
	};
	const exportHandler = async () => {
		const exportData = await exportCards(studentId, cardsetId);
	};

	const readAllCommentHandler = async () => {
		const params = { studentId, cardsetId, cardId: cards[idx].cardId };
		const readAllCommentData = await readAllComment(params);
		setComments(readAllCommentData);
	};

	const createCommentHandler = async () => {
		const params = {
			studentId,
			cardsetId,
			cardId: cards[idx].cardId,
			body: {
				title: commentTitle,
				content: commentContent,
				commenterId: studentId,
				cardId: cards[idx].cardId,
			},
		};
		const createCommentData = await createComment(params);
		setCommentTitle('');
		setCommentContent('');
		readAllCommentHandler();
	};

	const deleteCommentHandler = async () => {
		const cardsetsOwnByCurStudentId = await readAllCardset({
			studentId,
			cardsetCategory: null,
			cardsetStatus: null,
			keyword: null,
			limit: 1000,
			offset: 0,
		});
		let validDelete = false;
		cardsetsOwnByCurStudentId.objects.forEach((element) => {
			if (element.cardsetId.toString() === cardsetId) {
				validDelete = true;
			}
		});
		setValidDelete(validDelete);
	};

	const deleteCommentClickHandler = () => {
		readAllCommentHandler();
	};

	return (
		<>
			<div>cards</div>
			<div>
				<button onClick={exportHandler}>export whole cardset to excel</button>
			</div>
			{cards.length > 0 && <Card info={cards[idx]} />}
			{cards.length > 0 && <div>{`${idx + 1} / ${cards.length}`}</div>}
			{cards.length > 0 && (
				<div>
					<button
						className={styles.btn}
						onClick={prevHandler}
						disabled={idx === 0}
					>
						prev
					</button>
					<button
						className={styles.btn}
						onClick={nextHandler}
						disabled={idx === cards.length - 1}
					>
						next
					</button>
				</div>
			)}
			{localStorage.getItem('cardsetOwnerId') === studentId && (
				<div>
					<div>
						front title:{' '}
						<input
							type='text'
							value={upTitle}
							onChange={(e) => setUpTitle(e.target.value)}
						></input>
					</div>
					<div>
						front content:{' '}
						<input
							type='text'
							value={upContent}
							onChange={(e) => setUpContent(e.target.value)}
						></input>
					</div>
					<div>
						back title:{' '}
						<input
							type='text'
							value={downTitle}
							onChange={(e) => setDownTitle(e.target.value)}
						></input>
					</div>
					<div>
						back content:{' '}
						<input
							type='text'
							value={downContent}
							onChange={(e) => setDownContent(e.target.value)}
						></input>
					</div>
					<div>
						idx (only for update or delete):{' '}
						<input
							type='text'
							value={changeIdx}
							onChange={(e) => {
								setChangeIdx(e.target.value);
							}}
						></input>
					</div>
					<div>
						<button onClick={createHandler}>create card</button>
						<button
							onClick={updateHandler}
							disabled={changeIdx < 1 || changeIdx > cards.length}
						>
							update card
						</button>
						<button
							onClick={deleteHandler}
							disabled={changeIdx < 1 || changeIdx > cards.length}
						>
							delete card
						</button>
					</div>
				</div>
			)}
			{cards.length > 0 && (
				<div className={styles.commentArea}>
					<div>comments about this card</div>
					<div>
						<button onClick={readAllCommentHandler}>see comments</button>
					</div>
					{comments.map((item) => (
						<Comment
							onDelete={deleteCommentClickHandler}
							studentId={studentId}
							cardsetId={cardsetId}
							validDelete={validDelete}
							info={item}
							key={item.commentId.toString().concat(item.title)}
						/>
					))}
					<div>
						comment title:{' '}
						<input
							value={commentTitle}
							onChange={(e) => setCommentTitle(e.target.value)}
						></input>
					</div>
					<div>
						comment content:{' '}
						<input
							value={commentContent}
							onChange={(e) => setCommentContent(e.target.value)}
						></input>
					</div>
					<div>
						<button
							onClick={createCommentHandler}
							disabled={
								commentTitle.length === 0 || commentContent.length === 0
							}
						>
							add comment
						</button>
					</div>
				</div>
			)}
		</>
	);
};

export default Cards;
