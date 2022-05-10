const BrickCard = () => {
	return (
		<div class="card">
			<div class="card-image">
				<figure class="image is-4by3">
					<img src="img.jpg" alt="Placeholder image"></img>
				</figure>
			</div>
			<div class="card-content">
				<p class="title product-title">{this.props.product.name}</p>
				<a
					class="button is-primary"
					href="product.html"
					target="_blank"
				>
					<strong>More Details</strong>
				</a>
			</div>
		</div>
	);
};
