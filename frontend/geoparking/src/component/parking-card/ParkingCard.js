import { Box, Button } from "@material-ui/core";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
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
}));

export default function ParkingCard({ handleOpenBookSlotModal, parkingData }) {
	const classes = useStyles();

	const [isRaised, setIsRaised] = useState(false);

	return (
		<>
			<Card
				className={classes.root}
				onMouseOver={() => setIsRaised(true)}
				onMouseLeave={() => setIsRaised(false)}
				raised={isRaised}
			>
				<img
					className={classes.cover}
					src="/p1.jpg"
					alt="parking's image"
				/>
				<div className={classes.details}>
					<CardContent className={classes.content}>
						<Typography component="h5" variant="h5">
							{parkingData.name}
						</Typography>
						<Typography variant="subtitle1" color="textSecondary">
							Asansol
						</Typography>
						<Typography>{parkingData.location}</Typography>

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
								onClick={() =>
									handleOpenBookSlotModal(parkingData.id)
								}
							>
								Book Spot
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
					</CardContent>
				</div>
			</Card>
		</>
	);
}
