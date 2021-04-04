import {
	Box,
	Button,
	Card,
	CardContent,
	Divider,
	makeStyles,
	Typography,
} from "@material-ui/core";
import React, { useState } from "react";
import mapThumb from "../../assets/map_thumb.png";

const useStyles = makeStyles((theme) => ({
	root: {
		display: "flex",
		width: "auto",
		maxWidth: "30rem",
		// justifyContent: "center",
		// padding: "5px",
		// margin: "5px",
		// width: "350px",
		// height: "auto",
		// textAlign: "center",
		[theme.breakpoints.down("xs")]: {
			width: "90vw",
		},
	},

	cover: {
		width: 200,
		[theme.breakpoints.down("xs")]: {
			width: "35%",
		},
	},
	details: {
		display: "flex",
		flexDirection: "column",
	},
	content: {
		flex: "1 0 auto",
		textAlign: "center",
	},
	divider: {
		marginTop: 5,
		marginBottom: 5,
	},
}));

function MyBookedParkingCard() {
	const classes = useStyles();

	const [isRaised, setIsRaised] = useState(false);

	return (
		<Card
			className={classes.root}
			onMouseOver={() => setIsRaised(true)}
			onMouseLeave={() => setIsRaised(false)}
			raised={isRaised}
		>
			<img className={classes.cover} src="/p1.jpg" alt="imag" />
			<div className={classes.details}>
				<CardContent className={classes.content}>
					<Typography component="h5" variant="h5">
						{"parkingData.name"}
					</Typography>
					<Typography variant="subtitle1" color="textSecondary">
						Asansol
					</Typography>
					<Typography>{"parkingData.location"}</Typography>

					<Box
						display={"flex"}
						alignItems={"center"}
						justifyContent={"center"}
						marginTop={2}
					>
						<Button
							style={{ marginInline: 10 }}
							variant="contained"
							color="primary"
						>
							Receipt
						</Button>
						<img
							style={{
								height: 50,
								width: 35,
							}}
							src={mapThumb}
							alt="mapThumb"
						/>
					</Box>
					<Divider className={classes.divider} />
					<Box display="flex" justifyContent="space-between">
						<Box>
							<p>{new Date().toDateString()}</p>
							<p>
								{new Date(
									new Date().getTime() +
										2 * 24 * 60 * 60 * 1000
								).toDateString()}
							</p>
						</Box>
						<Box>
							<p>
								{new Date(
									new Date().getTime() - 1000 * 60 * 60
								).toLocaleTimeString()}
							</p>
							<p>{new Date().toLocaleTimeString()}</p>
						</Box>
					</Box>
					<Divider className={classes.divider} />
					<Button
						style={{ margin: 0 }}
						fullWidth
						variant="outlined"
						color="secondary"
					>
						Cancel
					</Button>
				</CardContent>
			</div>
		</Card>
	);
}

export default MyBookedParkingCard;
