import "../styles/components/MainSlider.css";

export default function MainSlider({ imageUrl, alt = "" }) {
  return (
    <div className="main-slider">
      <img src={imageUrl} alt={alt} className="main-slider-image" />
    </div>
  );
}
