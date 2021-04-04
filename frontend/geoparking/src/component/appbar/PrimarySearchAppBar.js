import { Button } from "@material-ui/core";
import AppBar from "@material-ui/core/AppBar";
import Badge from "@material-ui/core/Badge";
import IconButton from "@material-ui/core/IconButton";
import InputBase from "@material-ui/core/InputBase";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import { fade, makeStyles } from "@material-ui/core/styles";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { LocalParking, ReceiptOutlined } from "@material-ui/icons";
import AccountCircle from "@material-ui/icons/AccountCircle";
import MoreIcon from "@material-ui/icons/MoreVert";
import NotificationsIcon from "@material-ui/icons/Notifications";
import SearchIcon from "@material-ui/icons/Search";
import React, { useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { useAuth } from "../../authentication/ProvideAuth";
import logo from "../../logo.svg";

const useStyles = makeStyles((theme) => ({
	offset: theme.mixins.toolbar,
	grow: {
		flexGrow: 1,
	},
	menuButton: {
		marginRight: theme.spacing(0),
	},
	title: {
		display: "none",
		[theme.breakpoints.up("sm")]: {
			display: "block",
		},
	},
	search: {
		position: "relative",
		borderRadius: theme.shape.borderRadius,
		backgroundColor: fade(theme.palette.common.white, 0.15),
		"&:hover": {
			backgroundColor: fade(theme.palette.common.white, 0.25),
		},
		marginRight: theme.spacing(2),
		marginLeft: 0,
		width: "100%",
		[theme.breakpoints.up("sm")]: {
			marginLeft: theme.spacing(3),
			width: "auto",
		},
	},
	searchIcon: {
		padding: theme.spacing(0, 2),
		height: "100%",
		position: "absolute",
		pointerEvents: "none",
		display: "flex",
		alignItems: "center",
		justifyContent: "center",
	},
	inputRoot: {
		color: "inherit",
	},
	inputInput: {
		padding: theme.spacing(1, 1, 1, 0),
		// vertical padding + font size from searchIcon
		paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
		transition: theme.transitions.create("width"),
		width: "100%",
		[theme.breakpoints.up("md")]: {
			width: "20ch",
		},
	},
	sectionDesktop: {
		display: "none",
		[theme.breakpoints.up("md")]: {
			display: "flex",
		},
	},
	sectionMobile: {
		display: "flex",
		[theme.breakpoints.up("md")]: {
			display: "none",
		},
	},
}));

export default function PrimarySearchAppBar() {
	const auth = useAuth();
	const history = useHistory();

	const [isUserAuthenticated, setIsUserAuthenticated] = useState(
		auth.isUserLoggedIn || auth.isAdminLoggedIn
	);

	useEffect(() => {
		// console.log(auth);
		setIsUserAuthenticated(
			() => auth.isUserLoggedIn || auth.isAdminLoggedIn
		);
	}, [auth]);

	const logoutCallback = () => {
		history.push("/");
		setIsUserAuthenticated(false);
	};

	const classes = useStyles();
	const [anchorEl, setAnchorEl] = React.useState(null);
	const [mobileMoreAnchorEl, setMobileMoreAnchorEl] = React.useState(null);

	const isMenuOpen = Boolean(anchorEl);
	const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);

	const handleProfileMenuOpen = (event) => {
		setAnchorEl(event.currentTarget);
	};

	const handleMobileMenuClose = () => {
		setMobileMoreAnchorEl(null);
	};

	const handleMenuClose = () => {
		setAnchorEl(null);
		handleMobileMenuClose();
	};

	const handleMobileMenuOpen = (event) => {
		setMobileMoreAnchorEl(event.currentTarget);
	};

	const menuId = "primary-search-account-menu";
	const renderMenu = (
		<Menu
			anchorEl={anchorEl}
			anchorOrigin={{ vertical: "top", horizontal: "right" }}
			id={menuId}
			keepMounted
			transformOrigin={{ vertical: "top", horizontal: "right" }}
			open={isMenuOpen}
			onClose={handleMenuClose}
		>
			<MenuItem onClick={handleMenuClose}>
				<Link
					to="/my-bookings"
					style={{
						textDecoration: "none",
						color: "inherit",
						width: "100%",
						height: "100%",
					}}
				>
					My bookings
				</Link>
			</MenuItem>
			<MenuItem onClick={handleMenuClose}>
				<Link
					to="/my-account"
					style={{
						textDecoration: "none",
						color: "inherit",
						width: "100%",
						height: "100%",
					}}
				>
					Profile
				</Link>
			</MenuItem>
		</Menu>
	);

	const mobileMenuId = "primary-search-account-menu-mobile";
	const renderMobileMenu = (
		<Menu
			anchorEl={mobileMoreAnchorEl}
			anchorOrigin={{ vertical: "top", horizontal: "right" }}
			id={mobileMenuId}
			keepMounted
			transformOrigin={{ vertical: "top", horizontal: "right" }}
			open={isMobileMenuOpen}
			onClose={handleMobileMenuClose}
		>
			<MenuItem>
				<IconButton aria-label="show 4 new mails" color="inherit">
					<Badge badgeContent={4} color="secondary">
						<NotificationsIcon />
					</Badge>
				</IconButton>
				<p>Notification</p>
			</MenuItem>
			<MenuItem>
				<IconButton
					aria-label="show 11 new notifications"
					color="inherit"
				>
					<Badge badgeContent={1} color="secondary">
						<LocalParking />
					</Badge>
				</IconButton>
				<p>My bookings</p>
			</MenuItem>
			<MenuItem onClick={handleProfileMenuOpen}>
				<IconButton
					aria-label="account of current user"
					aria-controls="primary-search-account-menu"
					aria-haspopup="true"
					color="inherit"
				>
					<AccountCircle />
				</IconButton>
				<p>Profile</p>
			</MenuItem>
			<hr style={{ width: "80%" }} />
			<MenuItem>
				<LogoutBtn signout={() => auth.signout(logoutCallback)} />
			</MenuItem>
		</Menu>
	);

	return (
		<div className={classes.grow}>
			<AppBar position="fixed">
				<Toolbar>
					<IconButton
						edge="start"
						className={classes.menuButton}
						color="inherit"
						aria-label="open drawer"
						onClick={() => history.push("/")}
					>
						<img height="40px" src={logo} alt="Logo" />
					</IconButton>
					<Typography className={classes.title} variant="h6" noWrap>
						<Link
							to="/"
							style={{ textDecoration: "none", color: "white" }}
						>
							GeoParking
						</Link>
					</Typography>
					<div className={classes.search}>
						<div className={classes.searchIcon}>
							<SearchIcon />
						</div>
						<InputBase
							placeholder="Search…"
							classes={{
								root: classes.inputRoot,
								input: classes.inputInput,
							}}
							inputProps={{ "aria-label": "search" }}
						/>
					</div>
					<div className={classes.grow} />

					{!isUserAuthenticated ? (
						<Link to="/login" style={{ textDecoration: "none" }}>
							<Button variant={"contained"}>Login</Button>
						</Link>
					) : (
						<>
							<div className={classes.sectionDesktop}>
								<IconButton
									aria-label="show 4 new mails"
									color="inherit"
								>
									<Badge badgeContent={4} color="secondary">
										<ReceiptOutlined />
									</Badge>
								</IconButton>
								<IconButton
									aria-label="show 17 new notifications"
									color="inherit"
								>
									<Badge badgeContent={17} color="secondary">
										<NotificationsIcon />
									</Badge>
								</IconButton>
								<IconButton
									edge="end"
									aria-label="account of current user"
									aria-controls={menuId}
									aria-haspopup="true"
									onClick={handleProfileMenuOpen}
									color="inherit"
								>
									<AccountCircle />
								</IconButton>
								<div
									style={{
										display: "flex",
										alignItems: "center",
									}}
								>
									<LogoutBtn
										signout={() =>
											auth.signout(logoutCallback)
										}
									/>
								</div>
							</div>
							<div className={classes.sectionMobile}>
								<IconButton
									aria-label="show more"
									aria-controls={mobileMenuId}
									aria-haspopup="true"
									onClick={handleMobileMenuOpen}
									color="inherit"
								>
									<MoreIcon />
								</IconButton>
							</div>
						</>
					)}
				</Toolbar>
			</AppBar>
			{renderMobileMenu}
			{renderMenu}
			<div className={classes.offset} />
		</div>
	);
}

function LogoutBtn({ signout }) {
	return (
		<Button
			onClick={() => signout()}
			variant="contained"
			color="secondary"
			style={{
				marginLeft: 20,
				fontFamily: "sans-serif",
				fontWeight: "bold",
			}}
		>
			Logout
		</Button>
	);
}
