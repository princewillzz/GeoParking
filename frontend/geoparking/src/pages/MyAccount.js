import { makeStyles, Paper } from "@material-ui/core";
import { Close } from "@material-ui/icons";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { fetchProfileInfo } from "../api/profile-api";
import { useAuth } from "../authentication/ProvideAuth";
import BasicInfoEdit from "../component/my-account/BasicInfoEdit";
import EmailAddressEdit from "../component/my-account/EmailAddressEdit";
import MobileInfoEdit from "../component/my-account/MobileInfoEdit";

const useStyles = makeStyles((theme) => ({
	root: {
		minHeight: "100vh",
		height: "100%",
		backgroundColor: "grey",
		// backgroundColor: "#2f2f2f",
		// fontFamily: '"Roboto", sans-serif',
		// display: "flex",
		overflow: "hidden",
		paddingBottom: 100,
	},
	backContainer: {
		background: "#fdec51",
		maxWidth: 500,
		width: "100%",
		margin: "auto",
		padding: "0 60px",
		[theme.breakpoints.down("sm")]: {
			padding: 0,
		},
	},
	top: {
		display: "flex",
		justifyContent: "space-between",
		paddingTop: 40,
		marginBottom: 30,
		alignItems: "center",
		"&>p": {
			fontSize: "2rem",
		},
		[theme.breakpoints.down("sm")]: {
			marginInline: 10,
		},
	},
	bottom: {
		paddingBottom: 30,
		marginTop: 30,
		display: "flex",
		justifyContent: "flex-end",
		"& > *": {
			marginLeft: 20,
		},
		[theme.breakpoints.down("sm")]: {
			marginInline: 20,
		},
	},
	frontContainer: {
		background: "#fff",
		boxShadow: "0 5px 15px 2px rgba(0, 0, 0, 0.3)",
		width: 600,
		// minHeight: "80vh",
		position: "relative",
		margin: "auto",
		paddingBottom: 30,
		[theme.breakpoints.down("sm")]: {
			marginInline: 30,
			width: "100%",
		},
		[theme.breakpoints.down("xs")]: {
			marginInline: 0,
		},
	},
	profileAvatar: {
		borderRadius: "50%",
		width: 100,
		height: 100,
		position: "absolute",
		top: "-35px",
		left: "40px",
	},
}));

function MyAccount() {
	const styles = useStyles();

	const auth = useAuth();

	const [profile, setProfile] = useState({});

	useEffect(() => {
		fetchProfileInfo().then((profile) => setProfile(profile));
	}, []);

	return (
		<main className={styles.root}>
			{/* backcontainer to show a elevated effect */}
			<div className={styles.backContainer}>
				<div className={styles.top}>
					<p>Personal Information</p>
					<Link to="/">
						<Close />
					</Link>
				</div>
				{/* Front container containing all the details */}
				<div className={styles.frontContainer}>
					<div>
						<img
							className={styles.profileAvatar}
							src="/p2.jpg"
							alt="profile-avatar"
						/>
					</div>

					<Paper
						elevation={3}
						style={{
							display: "flex",
							alignItems: "center",
							flexDirection: "column",
							paddingBottom: 20,
						}}
					>
						<p style={{ fontWeight: 300, marginBottom: 10 }}>
							Hello,
						</p>
						<div style={{ fontWeight: 600 }}> {auth.username} </div>
					</Paper>

					<BasicInfoEdit profile={profile} />
					<EmailAddressEdit />
					<MobileInfoEdit />
				</div>
				{/* back containers bottom part containing save and cancel button */}
				<div className={styles.bottom}>
					{/* <Button>Reset</Button>
					<Button variant="outlined">Save</Button> */}
				</div>
			</div>
		</main>
	);
}

export default MyAccount;
